package ru.gb.externalapi.rest.mapper;

import org.mapstruct.Mapper;
import ru.gb.externalapi.entity.security.AccountUser;
import ru.gb.gbapimay.security.UserDto;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/
@Mapper
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}