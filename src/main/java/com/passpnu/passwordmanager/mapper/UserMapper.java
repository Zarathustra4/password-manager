package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    AuthUserDetailsDto userEntityToAuth(UserEntity entity);
}
