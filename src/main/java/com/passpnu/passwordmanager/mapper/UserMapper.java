package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AuthUserDetailsDto userEntityToAuth(UserEntity entity);
}
