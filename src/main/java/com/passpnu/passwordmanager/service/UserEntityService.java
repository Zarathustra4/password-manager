package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.UserDto;
import com.passpnu.passwordmanager.entity.UserEntity;

import javax.naming.NameNotFoundException;


public interface UserEntityService
{
    UserEntity saveUser(UserDto user);

    Boolean existsByUsername(String username);

    Long getIdByUsername(String username);

    UserEntity getUserById(Long id) throws NameNotFoundException;
}
