package com.solarsan.blog.service;

import com.google.protobuf.Empty;
import com.solarsan.blog.*;
import com.solarsan.blog.model.UserEntity;
import com.solarsan.blog.repository.BlogRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
public class BlogService extends BlogServiceGrpc.BlogServiceImplBase {

    private final BlogRepository repository;

    public BlogService(final BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(final NewUserRequest request, final StreamObserver<IdResponse> responseObserver) {
        final String name = request.getName();
        final UUID id = UUID.randomUUID();

        final UserEntity user = new UserEntity(id, name);
        System.out.printf("Created new user %s.%n", name);
        repository.save(user);

        final IdResponse idResponse = IdResponse.newBuilder().setId(id.toString()).build();
        responseObserver.onNext(idResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(final IdRequest request, final StreamObserver<UserResponse> responseObserver) {
        final UUID id = UUID.fromString(request.getId());

        System.out.printf("Request for user with id %s.%n", id);
        final UserEntity user = repository.get(id).get();

        final UserResponse userResponse =
                UserResponse.newBuilder().setId(user.getId().toString()).setName(user.getName()).build();
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsers(final Empty request, final StreamObserver<UsersResponse> responseObserver) {
        System.out.println("Request for all users.");
        final List<UserEntity> users = repository.getAll();

        final List<UserResponse> userResponses = users.stream().map(
                u -> UserResponse.newBuilder().setId(u.getId().toString()).setName(u.getName()).build()
        ).collect(Collectors.toList());

        final UsersResponse usersResponse = UsersResponse.newBuilder().addAllUsers(userResponses).build();
        responseObserver.onNext(usersResponse);
        responseObserver.onCompleted();
    }

}
