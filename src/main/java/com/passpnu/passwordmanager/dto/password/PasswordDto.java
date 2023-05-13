package com.passpnu.passwordmanager.dto.password;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordDto {
    private String password;
    private Long serviceId;
    private Long userId;
}
