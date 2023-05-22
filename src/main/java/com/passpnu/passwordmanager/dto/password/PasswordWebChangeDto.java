package com.passpnu.passwordmanager.dto.password;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordWebChangeDto {
    private String password;
    private String serviceDomain;
}
