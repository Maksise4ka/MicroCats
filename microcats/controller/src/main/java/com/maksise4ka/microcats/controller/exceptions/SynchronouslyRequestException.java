package com.maksise4ka.microcats.controller.exceptions;

public class SynchronouslyRequestException extends ControllerException {
    private SynchronouslyRequestException(String message) {
        super(message);
    }

    public static SynchronouslyRequestException kafkaThrowsException() {
        return new SynchronouslyRequestException("Something went wrong during sending request");
    }

    public static <T, R> SynchronouslyRequestException unexpectedTypeReceived(Class<T> awaitedType, Class<R> receivedType) {
        return new SynchronouslyRequestException("Await type: " + awaitedType + " but received " + receivedType);
    }
}
