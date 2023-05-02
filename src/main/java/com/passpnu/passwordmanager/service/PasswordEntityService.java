package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AuthPasswordDto;
import com.passpnu.passwordmanager.dto.GuestPasswordDto;

import javax.naming.NameNotFoundException;

public interface PasswordEntityService {
    GuestPasswordDto generatePassword();
    AuthPasswordDto storePassword(AuthPasswordDto passwordDto) throws NameNotFoundException;
}
