package ru.gb.gbshopmay.service;


import ru.gb.gbapimay.security.UserDto;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.entity.token.ConfirmationToken;

import java.util.List;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
public interface UserService {

    UserDto register(UserDto userDto);
    void generateConfirmationToken(UserDto userDto, String token);
    ConfirmationToken getConfirmationToken(String token);
    UserDto update(UserDto userDto);
    AccountUser update(AccountUser accountUser);
    AccountUser findByUsername(String username);
//    AccountUser update(AccountUser userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void deleteById(Long id);

    // todo дз 9 добавить метод обработки кода подтверждения
}
