package com.passpnu.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthPasswordDto {
    private String password;
    private String serviceDomain;
    private String username;
}