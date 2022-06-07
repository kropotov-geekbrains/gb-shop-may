package ru.gb.externalapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends IllegalArgumentException {

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }
}