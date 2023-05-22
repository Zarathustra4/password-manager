package com.passpnu.passwordmanager.dto.password;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordWebDto {
    private String serviceName;
    private String password;
    private String serviceDomain;
}
