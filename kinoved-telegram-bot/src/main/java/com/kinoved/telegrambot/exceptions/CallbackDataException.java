package com.kinoved.telegrambot.exceptions;

public class CallbackDataException extends RuntimeException {
    public CallbackDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
