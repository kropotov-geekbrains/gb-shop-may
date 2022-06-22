package ru.gb.externalapi.exception;

public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
