package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AuthPasswordDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.GuestPasswordDto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PasswordEntityService {
    GuestPasswordDto generatePassword();

    boolean savePassword(AuthPasswordDto authPasswordDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
