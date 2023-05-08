package com.passpnu.passwordmanager.dto;

import com.passpnu.passwordmanager.entity.Role;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserDto {
    private String username;
    private String encryptionKey;
    private String password;
    private Role role;
}
