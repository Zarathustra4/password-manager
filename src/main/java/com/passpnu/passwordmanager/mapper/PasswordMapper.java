package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.AuthPasswordDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import com.passpnu.passwordmanager.entity.UserEntity;
import com.passpnu.passwordmanager.repos.ServiceRepository;
import com.passpnu.passwordmanager.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@AllArgsConstructor
public class PasswordMapper {
    //TODO Remove repository and encryptor. Gather it's data from outside
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final PasswordEncryptor passwordEncryptor;

    public PasswordEntity passwordDtoToEntity(AuthPasswordDto passwordDto, String encryptionKey)
            throws NameNotFoundException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String username = passwordDto.getUsername();
        String domain = passwordDto.getServiceDomain();
        String encryptedPassword = passwordEncryptor.encrypt(passwordDto.getPassword(), encryptionKey);

        UserEntity userEntity = userRepository.getByUsername(username);
        if(userEntity == null){
            throw new NameNotFoundException("There is no such a user with the username - " + username);
        }

        ServiceEntity serviceEntity = serviceRepository.findByDomain(domain);
        if(serviceEntity == null){
            throw new NameNotFoundException("There is no such a service with the domain - " + domain);
        }

        return PasswordEntity.builder()
                .user(userEntity)
                .service(serviceEntity)
                .password(encryptedPassword)
                .build();
    }
}
