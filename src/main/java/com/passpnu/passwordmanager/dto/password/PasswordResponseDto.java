package com.passpnu.passwordmanager.dto.password;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResponseDto {
    private String password;
}
