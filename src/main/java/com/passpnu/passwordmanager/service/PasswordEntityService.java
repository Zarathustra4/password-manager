package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.password.PasswordRequestDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.password.PasswordResponseDto;
import com.passpnu.passwordmanager.exception.EncryptionException;


import javax.naming.NameNotFoundException;


public interface PasswordEntityService {
    PasswordResponseDto generatePassword();

    void savePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, EncryptionException;

    PasswordResponseDto getPassword(Long serviceId, AuthUserDetailsDto userDto) throws EncryptionException;

    PasswordResponseDto generateAndStorePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, EncryptionException;
    void changePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, EncryptionException;
    void deletePassword(Long serviceId, AuthUserDetailsDto userDto);
}
