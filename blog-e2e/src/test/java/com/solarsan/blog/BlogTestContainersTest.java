package com.solarsan.blog;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class BlogTestContainersTest {

    private static final String IMAGE_NAME = "docker.io/library/blog-server:0.0.1";
    private static final int PORT = 9090;

    @Container
    private static final GenericContainer<?> APP = new GenericContainer<>(IMAGE_NAME).withExposedPorts(PORT);

    @Test
    void test() throws InterruptedException {
        BlogTestBase.createUserAndNoteAndReceiveThem(APP.getHost() + ":" + PORT);
    }

}
