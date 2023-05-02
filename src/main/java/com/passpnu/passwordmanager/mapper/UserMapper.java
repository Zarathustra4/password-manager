package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.AuthUserDetails;
import com.passpnu.passwordmanager.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public AuthUserDetails userEntityToUserDetails(UserEntity userEntity){
        return AuthUserDetails.builder()
                .username(userEntity.getUsername())
                .encryptionKey(userEntity.getEncryptionKey())
                .role(userEntity.getRole())
                .password(userEntity.getPassword())
                .build();
    }
}
