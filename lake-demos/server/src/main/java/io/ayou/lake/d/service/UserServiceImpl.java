package io.ayou.lake.d.service;

import io.ayou.lake.d.api.UserService;
import io.ayou.lake.d.domain.User;
import io.ayou.lake.demo.rpc.autoconfiguration.RPCService;

import java.time.Instant;

@RPCService(UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User get(String id) {
        return new User(id, "lake", 27, Instant.now());
    }
}
