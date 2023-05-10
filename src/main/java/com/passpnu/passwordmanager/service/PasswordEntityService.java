package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.PasswordRequestDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.PasswordResponseDto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PasswordEntityService {
    PasswordResponseDto generatePassword();

    void savePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    PasswordResponseDto getPassword(Long serviceId, AuthUserDetailsDto userDto) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    PasswordResponseDto generateAndStorePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void changePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void deletePassword(Long serviceId, AuthUserDetailsDto userDto);
}
