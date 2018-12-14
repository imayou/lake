package io.ayou.lake.provider;

import io.ayou.lake.container.spring.annotion.RpcService;
import io.ayou.lake.example.api.api.UserService;
import io.ayou.lake.example.api.domain.User;

import java.time.Instant;

@RpcService(UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User get(String id) {
        return new User(id, "lake", 27, Instant.now());
    }
}
