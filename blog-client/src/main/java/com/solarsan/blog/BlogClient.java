package com.solarsan.blog;

import com.google.protobuf.Empty;
import io.grpc.Channel;

import java.util.List;
import java.util.UUID;

public class BlogClient {

    private final BlogServiceGrpc.BlogServiceBlockingStub blockingStub;

    public BlogClient(final Channel channel) {
        blockingStub = BlogServiceGrpc.newBlockingStub(channel);
    }

    public UUID createUser(final String name) {
        System.out.printf("Client wants to create user with name %s.%n", name);
        final NewUserRequest request = NewUserRequest.newBuilder().setName(name).build();
        final IdMessage id = blockingStub.createUser(request);
        return UUID.fromString(id.getId());
    }

    public void createNote(final UUID userId, final Note note) {
        System.out.printf("Client wants to create note for user with id %s.%n", userId);
        final NewNoteRequest request = NewNoteRequest.newBuilder().setUserId(userId.toString()).setNote(note).build();
        blockingStub.createNote(request);
    }

    public User getUser(final UUID id) {
        System.out.printf("Client wants to get user with id %s.%n", id);
        final IdMessage request = IdMessage.newBuilder().setId(id.toString()).build();
        return blockingStub.getUser(request);
    }

    public List<User> getUsers() {
        System.out.println("Client wants to get all users");
        return blockingStub.getUsers(Empty.getDefaultInstance()).getUsersList();
    }

}
