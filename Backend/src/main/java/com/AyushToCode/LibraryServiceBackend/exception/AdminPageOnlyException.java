package com.AyushToCode.LibraryServiceBackend.exception;

public class AdminPageOnlyException extends RuntimeException {
    public AdminPageOnlyException(String message) {
        super(message);
    }
}
