package com.solarsan.blog.repository;

import com.solarsan.blog.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BlogRepository {
    private final Map<UUID, User> users;

    public BlogRepository() {
        this.users = new HashMap<>();
    }

    public void save(final User user) {
        users.put(UUID.fromString(user.getId()), user);
    }

    public User get(final UUID id) {
        return users.get(id);
    }

    public List<User> getAll() {
        return List.copyOf(users.values());
    }
}
