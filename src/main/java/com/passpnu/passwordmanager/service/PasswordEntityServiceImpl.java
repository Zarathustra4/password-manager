package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.PasswordRequestDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.PasswordResponseDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;

import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;

import com.passpnu.passwordmanager.repos.PasswordRepository;
import jakarta.transaction.Transactional;
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

    private final PasswordEncryptor passwordEncryptor;
    private StringPasswordGenerator passwordGenerator;
    private final PasswordRepository passwordRepository;
    private final UserEntityService userService;
    private final ServiceEntityService serviceEntityService;
    private static final String ENCRYPTION_EXCEPTION_MESSAGE = "Trying to encrypt caused an error";

    public PasswordResponseDto generatePassword(){
        return PasswordResponseDto.builder()
                .password(passwordGenerator.generate())
                .build();
    }

    @Override
    public PasswordResponseDto getPassword(Long serviceId, AuthUserDetailsDto userDto) throws EncryptionException {

        Long userId = userService.getIdByUsername(userDto.getUsername());
        PasswordEntity passwordEntity = passwordRepository.findByUserIdAndServiceId(userId, serviceId);
        String decodedPassword;
        try {
            decodedPassword = passwordEncryptor.decrypt(passwordEntity.getPassword(), userDto.getEncryptionKey());
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException |
                NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex){
            throw new EncryptionException(ENCRYPTION_EXCEPTION_MESSAGE);
        }
        return PasswordResponseDto.builder().password(decodedPassword).build();
    }

    @Override
    public void savePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException {

        String encodedPassword;

        Long userId = userDto.getId();
        try {
            encodedPassword = passwordEncryptor.encrypt(passwordRequestDto.getPassword(), userDto.getEncryptionKey());
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException |
               NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex){
            throw new EncryptionException(ENCRYPTION_EXCEPTION_MESSAGE);
        }
        Long serviceId = passwordRequestDto.getServiceId();

        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);


        if(passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            //change to relevant exception
            throw new NameNotFoundException(
                    "There already exists the password for %s service"
                            .formatted(serviceEntity.getDomain())
            );
        }


        PasswordEntity passwordEntity = PasswordEntity.builder()
                .serviceId(serviceId)
                .userId(userId)
                .password(encodedPassword)
                .build();

        passwordRepository.save(passwordEntity);
    }

    @Override
    public PasswordResponseDto generateAndStorePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException {
        Long userId = userDto.getId();

        Long serviceId = passwordRequestDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);


        if(passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            //change to relevant exception
            throw new NameNotFoundException(
                    "There already exists the password for %s service"
                            .formatted(serviceEntity.getDomain())
            );
        }
        String generatedPassword = passwordGenerator.generate();
        String encodedPassword;
        try {
            encodedPassword = passwordEncryptor.encrypt(generatedPassword, userDto.getEncryptionKey());
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException |
               NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex){
            throw new EncryptionException(ENCRYPTION_EXCEPTION_MESSAGE);
        }
        PasswordResponseDto passwordResponseDto = PasswordResponseDto.builder().password(generatedPassword).build();

        PasswordEntity passwordEntity = PasswordEntity.builder()
                .serviceId(serviceId)
                .userId(userId)
                .password(encodedPassword)
                .build();

        passwordRepository.save(passwordEntity);

        return passwordResponseDto;
    }

    @Override
    public void changePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException {
        Long userId = userDto.getId();

        Long serviceId = passwordRequestDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);

        if(!passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            //change to relevant exception
            throw new NameNotFoundException(
                    "There is not such a password for %s service"
                            .formatted(serviceEntity.getDomain())
            );
        }

        PasswordEntity passwordEntity = passwordRepository.findByUserIdAndServiceId(userId, serviceId);
        String encryptedPassword;
        try{
            encryptedPassword = passwordEncryptor.encrypt(
                    passwordRequestDto.getPassword(),
                    userDto.getEncryptionKey()
            );
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException |
               NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex){
            throw new EncryptionException(ENCRYPTION_EXCEPTION_MESSAGE);
        }


        passwordEntity.setPassword(encryptedPassword);

        passwordRepository.save(passwordEntity);
    }

    @Override
    @Transactional
    public void deletePassword(Long serviceId, AuthUserDetailsDto userDto) {
        Long userId = userDto.getId();
        passwordRepository.deleteByUserIdAndServiceId(userId, serviceId);
    }
}
