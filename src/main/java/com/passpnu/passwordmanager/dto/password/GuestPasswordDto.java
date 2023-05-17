package com.passpnu.passwordmanager.dto.password;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestPasswordDto {
    private String password;
    private String rest;
}
