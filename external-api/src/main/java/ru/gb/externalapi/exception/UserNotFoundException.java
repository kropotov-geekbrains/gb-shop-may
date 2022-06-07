package ru.gb.externalapi.exception;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}