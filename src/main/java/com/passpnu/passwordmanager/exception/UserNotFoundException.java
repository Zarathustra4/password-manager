package com.passpnu.passwordmanager.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {}

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
