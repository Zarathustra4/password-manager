package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.GuestPasswordDto;

public interface PasswordEntityService {
    GuestPasswordDto generatePassword();
}
