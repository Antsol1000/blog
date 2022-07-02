package com.solarsan.blog.model;

import java.util.UUID;

public class UserEntity {
    private final UUID id;
    private final String name;

    public UserEntity(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
