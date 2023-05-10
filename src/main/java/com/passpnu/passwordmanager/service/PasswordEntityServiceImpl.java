package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.PasswordRequestDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.PasswordResponseDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import com.passpnu.passwordmanager.entity.UserEntity;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;
import com.passpnu.passwordmanager.mapper.CustomPasswordMapper;
import com.passpnu.passwordmanager.mapper.PasswordMapper;
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
    private final CustomPasswordMapper customPasswordMapper;
    private final PasswordMapper passwordMapper;

    public PasswordResponseDto generatePassword(){
        return PasswordResponseDto.builder()
                .password(passwordGenerator.generate())
                .build();
    }

    @Override
    public PasswordResponseDto getPassword(Long serviceId, AuthUserDetailsDto userDto)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        Long userId = userService.getIdByUsername(userDto.getUsername());
        PasswordEntity passwordEntity = passwordRepository.findByUserIdAndServiceId(userId, serviceId);

        String decodedPassword = passwordEncryptor.decrypt(passwordEntity.getPassword(), userDto.getEncryptionKey());
        return PasswordResponseDto.builder().password(decodedPassword).build();
    }

    @Override
    public void savePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NameNotFoundException {

        Long userId = userDto.getId();
        String encodedPassword = passwordEncryptor.encrypt(passwordRequestDto.getPassword(), userDto.getEncryptionKey());
        Long serviceId = passwordRequestDto.getServiceId();

        UserEntity userEntity = userService.getUserById(userId);
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
                .user(userEntity)
                .service(serviceEntity)
                .password(encodedPassword)
                .build();

        passwordRepository.save(passwordEntity);
    }

    @Override
    public PasswordResponseDto generateAndStorePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Long userId = userDto.getId();

        Long serviceId = passwordRequestDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);
        UserEntity userEntity = userService.getUserById(userId);

        if(passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            //change to relevant exception
            throw new NameNotFoundException(
                    "There already exists the password for %s service"
                            .formatted(serviceEntity.getDomain())
            );
        }
        String generatedPassword = passwordGenerator.generate();
        String encodedPassword = passwordEncryptor.encrypt(generatedPassword, userDto.getEncryptionKey());

        PasswordResponseDto passwordResponseDto = PasswordResponseDto.builder().password(generatedPassword).build();

        PasswordEntity passwordEntity = PasswordEntity.builder()
                .serviceId(serviceId)
                .userId(userId)
                .user(userEntity)
                .service(serviceEntity)
                .password(encodedPassword)
                .build();

        passwordRepository.save(passwordEntity);

        return passwordResponseDto;
    }

    @Override
    public void changePassword(PasswordRequestDto passwordRequestDto, AuthUserDetailsDto userDto) throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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

        String encryptedPassword = passwordEncryptor.encrypt(
                passwordRequestDto.getPassword(),
                userDto.getEncryptionKey()
        );

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
