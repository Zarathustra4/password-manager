package com.passpnu.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestPasswordDto {
    private String password;
}
