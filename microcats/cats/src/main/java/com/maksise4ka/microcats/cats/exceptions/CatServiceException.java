package com.maksise4ka.microcats.cats.exceptions;

public class CatServiceException extends Exception {
    public CatServiceException(String message) {
        super(message);
    }

    public static CatServiceException SameId(long id) {
        return new CatServiceException("Can't make or stop make a cat " + id + " friend with itself");
    }

    public static CatServiceException AlreadyFriends(long id1, long id2) {
        return new CatServiceException("Cat " + id1 + " have already made a friend with a cat " + id2);
    }
}
