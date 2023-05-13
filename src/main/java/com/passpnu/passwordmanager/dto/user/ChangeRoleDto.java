package com.passpnu.passwordmanager.dto.user;

import com.passpnu.passwordmanager.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeRoleDto {
    private String username;
    private Role role;
}