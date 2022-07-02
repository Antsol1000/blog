package com.solarsan.blog.service;

import com.google.protobuf.Empty;
import com.solarsan.blog.*;
import com.solarsan.blog.repository.BlogRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;

@GrpcService
public class BlogService extends BlogServiceGrpc.BlogServiceImplBase {

    private final BlogRepository repository;

    public BlogService(final BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(final NewUserRequest request, final StreamObserver<IdMessage> responseObserver) {
        final String name = request.getName();
        final UUID id = UUID.randomUUID();

        final User user = User.newBuilder().setId(id.toString()).setName(name).build();
        System.out.printf("Created new user %s.%n", name);
        repository.save(user);

        final IdMessage idMessage = IdMessage.newBuilder().setId(id.toString()).build();
        responseObserver.onNext(idMessage);
        responseObserver.onCompleted();
    }

    @Override
    public void createNote(final NewNoteRequest request, final StreamObserver<Empty> responseObserver) {
        final UUID userId = UUID.fromString(request.getUserId());
        final Note note = request.getNote();

        final User user = repository.get(userId).toBuilder().addNotes(note).build();
        System.out.printf("Created new note for user %s.%n", user.getName());
        repository.save(user);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(final IdMessage request, final StreamObserver<User> responseObserver) {
        final UUID userId = UUID.fromString(request.getId());

        System.out.printf("Requested for user with id %s.%n", userId);
        final User user = repository.get(userId);

        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsers(final Empty request, final StreamObserver<UsersResponse> responseObserver) {
        System.out.println("Requested for all users.");
        final List<User> users = repository.getAll();

        final UsersResponse usersResponse = UsersResponse.newBuilder().addAllUsers(users).build();

        responseObserver.onNext(usersResponse);
        responseObserver.onCompleted();
    }
}
