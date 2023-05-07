package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.GuestPasswordDto;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordEntityServiceImpl implements PasswordEntityService{

    private final StringPasswordGenerator passwordGenerator;

    public GuestPasswordDto generatePassword(){
        return GuestPasswordDto.builder()
                .password(passwordGenerator.generate())
                .build();
    }

}
