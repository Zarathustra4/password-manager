package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.user.ChangeRoleDto;
import com.passpnu.passwordmanager.dto.user.UserDto;
import com.passpnu.passwordmanager.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.naming.NameNotFoundException;


public interface UserEntityService
{
    void saveUser(UserDto user);

    void saveAdmin(UserDto user);

    Boolean existsByUsername(String username);

    Long getIdByUsername(String username);

    UserEntity getUserById(Long id) throws NameNotFoundException;

    void changeRole(ChangeRoleDto changeRoleDto) throws UsernameNotFoundException;
}
