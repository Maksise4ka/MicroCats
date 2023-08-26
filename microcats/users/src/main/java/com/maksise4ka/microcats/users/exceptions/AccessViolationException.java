package com.maksise4ka.microcats.users.exceptions;

public class AccessViolationException extends RuntimeException {
    private AccessViolationException(String message) {
        super(message);
    }

    public static AccessViolationException userHasNotAccess(String login) {
        return new AccessViolationException("User " + login + " hasn't access to this operation");
    }
}
