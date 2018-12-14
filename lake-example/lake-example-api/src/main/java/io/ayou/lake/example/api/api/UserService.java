package io.ayou.lake.example.api.api;


import io.ayou.lake.example.api.domain.User;

public interface UserService {
    User get(String id);
}
