package com.solarsan.blog.repository;

import com.solarsan.blog.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BlogRepository {
    private final List<UserEntity> users;

    public BlogRepository() {
        this.users = new ArrayList<>();
    }

    public void save(final UserEntity user) {
        users.add(user);
    }

    public Optional<UserEntity> get(final UUID id) {
        return users.stream().filter(x -> x.getId().equals(id)).findAny();
    }

    public List<UserEntity> getAll() {
        return List.copyOf(users);
    }
}
