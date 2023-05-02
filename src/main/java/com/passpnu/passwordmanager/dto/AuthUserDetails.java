package com.passpnu.passwordmanager.dto;

import com.passpnu.passwordmanager.entity.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
public class AuthUserDetails {
    private String username;
    private String encryptionKey;
    private String password;
    private Role role;


}
