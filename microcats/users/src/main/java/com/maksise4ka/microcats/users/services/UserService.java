package com.maksise4ka.microcats.users.services;

import com.maksise4ka.microcats.dao.entities.authorize.User;
import com.maksise4ka.microcats.users.users.CurrentUser;

public interface UserService {
    CurrentUser getUser(Long id);
    CurrentUser getUser(String login);

    boolean userExists(String login);

    User saveOwnerUser(String login, String password);
}
