package com.passpnu.passwordmanager.dto.user;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserDto {
    private String username;
    private String encryptionKey;
    private String password;
}
