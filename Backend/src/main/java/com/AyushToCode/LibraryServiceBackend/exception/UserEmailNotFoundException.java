package com.AyushToCode.LibraryServiceBackend.exception;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}
