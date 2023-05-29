package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.password.PasswordServiceIdDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.password.PasswordResponseDto;
import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.exception.PasswordServiceMappingException;
import com.passpnu.passwordmanager.exception.ServiceOccupiedException;
import com.passpnu.passwordmanager.util.PasswordTestAnswer;


import javax.naming.NameNotFoundException;
import java.util.List;



public interface PasswordEntityService {
    PasswordResponseDto generatePassword();

    void savePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, ServiceOccupiedException;

    PasswordResponseDto getPassword(Long serviceId, AuthUserDetailsDto userDto) throws EncryptionException;

    PasswordResponseDto generateAndStorePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, ServiceOccupiedException;
    void changePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, PasswordServiceMappingException;
    void deletePassword(Long serviceId, AuthUserDetailsDto userDto);

    PasswordTestAnswer checkPasswordStrength(String password);

    List<AnalysisDto> analyzeSystem(AuthUserDetailsDto authUserDetailsDto)
            throws EncryptionException, NameNotFoundException;

    List<PasswordServiceIdDto> getPasswordList(AuthUserDetailsDto userDetailsDto);
}
