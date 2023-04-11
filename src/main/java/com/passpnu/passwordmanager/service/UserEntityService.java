package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.Dto.UserDto;
import com.passpnu.passwordmanager.entity.UserEntity;


public interface UserEntityService
{
    UserEntity saveUser(UserDto user);

    Boolean existsByUsername(String username);
}
