package com.passpnu.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordRequestDto {
    private String password;
    private Long serviceId;
}