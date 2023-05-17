package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.password.PasswordDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.exception.EncryptionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CustomPasswordMapper {
    private final PasswordMapper passwordMapper;

    public List<PasswordDto> entityListToDtoList(List<PasswordEntity> entities){
        List<PasswordDto> passwordDtoList = new ArrayList<>();
        for(PasswordEntity entity: entities){
            passwordDtoList.add(
                    passwordMapper.entityToDto(entity)
            );
        }
        return passwordDtoList;
    }

    public PasswordDto entityToDecryptedDto(PasswordEntity entity,
                                            PasswordEncryptor passwordEncryptor,
                                            String encryptionKey) throws EncryptionException {
        PasswordDto passwordDto;
        try{
            passwordDto = PasswordDto.builder()
                    .serviceId(entity.getServiceId())
                    .userId(entity.getUserId())
                    .password(passwordEncryptor.decrypt(entity.getPassword(), encryptionKey))
                    .build();
        }
        catch(NoSuchPaddingException | IllegalBlockSizeException |
                NoSuchAlgorithmException | BadPaddingException | InvalidKeyException exception){
            throw new EncryptionException("Decryption failed");
        }
        return passwordDto;
    }
}
