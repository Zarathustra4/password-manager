package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AuthPasswordDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.GuestPasswordDto;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;
import com.passpnu.passwordmanager.mapper.PasswordMapper;
import com.passpnu.passwordmanager.repos.PasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class PasswordEntityServiceImpl implements PasswordEntityService{

    private final StringPasswordGenerator passwordGenerator;
    private final PasswordMapper passwordMapper;
    private final PasswordRepository passwordRepository;

    public GuestPasswordDto generatePassword(){
        return GuestPasswordDto.builder()
                .password(passwordGenerator.generate())
                .build();
    }

    @Override
    public boolean savePassword(AuthPasswordDto authPasswordDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        authPasswordDto.setUsername(userDto.getUsername());
        PasswordEntity passwordEntity = passwordMapper.passwordDtoToEntity(authPasswordDto, userDto.getEncryptionKey());
        passwordRepository.save(passwordEntity);
        return true;
    }
}
