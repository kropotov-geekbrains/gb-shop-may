package ru.gb.externalapi.service;

import ru.gb.externalapi.entity.security.AccountUser;
import ru.gb.gbapimay.security.UserDto;

import java.util.List;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
public interface UserService {

    UserDto register(UserDto userDto);
    UserDto update(UserDto userDto);
    AccountUser findByUsername(String username);
//    AccountUser update(AccountUser userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void deleteById(Long id);
}