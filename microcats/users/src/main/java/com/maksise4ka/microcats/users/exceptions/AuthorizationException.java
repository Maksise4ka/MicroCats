package com.maksise4ka.microcats.users.exceptions;

public class AuthorizationException extends RuntimeException {
    private AuthorizationException(String message) {
        super(message);
    }

    public static AuthorizationException usernameNotFound(String login) {
        return new AuthorizationException("User with login " + login + " doesn't exist");
    }

    public static AuthorizationException ownerNotFound(String login) {
        return new AuthorizationException("Owner with username " + login + " not exists");
    }
}
