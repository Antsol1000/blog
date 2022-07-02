package com.solarsan.blog;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

class BlogTestBase {
    static void createUserAndNoteAndReceiveThem(final String target) throws InterruptedException {
        final ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        final BlogClient client = new BlogClient(channel);

        final UUID userId = client.createUser("user");

        final Note note = Note.newBuilder().setTitle("title").setCategory(Category.SPORT).setContent("content").build();
        client.createNote(userId, note);

        final User user = client.getUser(userId);

        Assertions.assertEquals(userId.toString(), user.getId());
        Assertions.assertEquals("user", user.getName());
        Assertions.assertArrayEquals(user.getNotesList().toArray(), new Note[]{note});

        client.createUser("user2");

        final List<User> users = client.getUsers();
        final var userNames = users.stream().map(User::getName).toArray();
        Assertions.assertArrayEquals(userNames, new String[]{"user", "user2"});

        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

}
