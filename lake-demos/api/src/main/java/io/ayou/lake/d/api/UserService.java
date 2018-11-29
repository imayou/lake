package io.ayou.lake.d.api;

import io.ayou.lake.d.domain.User;

public interface UserService {
    User get(String id);
}
