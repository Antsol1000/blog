package com.solarsan.blog;

import org.junit.jupiter.api.Test;

class BlogE2eTest {

    private static final String TARGET = "localhost:9090";

    @Test
    void test() throws InterruptedException {
        BlogTestBase.createUserAndNoteAndReceiveThem(TARGET);
    }
}
