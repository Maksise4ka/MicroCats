package com.maksise4ka.microcats.controller.exceptions;

public abstract class ControllerException extends RuntimeException {

    protected ControllerException(String message) {
        super(message);
    }
}
