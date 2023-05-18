package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.password.PasswordServiceIdDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.password.PasswordResponseDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;

import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.exception.PasswordServiceMappingException;
import com.passpnu.passwordmanager.exception.ServiceOccupiedException;
import com.passpnu.passwordmanager.exception.UncheckedEncryptionException;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;

import com.passpnu.passwordmanager.mapper.CustomPasswordMapper;
import com.passpnu.passwordmanager.repos.PasswordRepository;
import com.passpnu.passwordmanager.util.PasswordTestAnswer;
import com.passpnu.passwordmanager.util.PasswordUtil;
import com.passpnu.passwordmanager.util.StringSimilarityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.List;


@Service
@AllArgsConstructor
public class PasswordEntityServiceImpl implements PasswordEntityService{

    private final PasswordEncryptor passwordEncryptor;
    private final StringPasswordGenerator passwordGenerator;
    private final PasswordRepository passwordRepository;
    private final CustomPasswordMapper customPasswordMapper;
    private final UserEntityService userService;
    private final ServiceEntityService serviceEntityService;
    private final PasswordUtil passwordUtil;
    private final StringSimilarityUtil stringSimilarityUtil;

    private static final String ENCRYPTION_EXCEPTION_MESSAGE = "Trying to encrypt caused an error";


    public PasswordTestAnswer checkPasswordStrength(String password){
        return passwordUtil.isPasswordStrong(password);
    }

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
    public void savePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, ServiceOccupiedException {

        String encodedPassword;

        Long userId = userDto.getId();
        try {
            encodedPassword = passwordEncryptor.encrypt(passwordServiceIdDto.getPassword(), userDto.getEncryptionKey());
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException |
               NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex){
            throw new EncryptionException(ENCRYPTION_EXCEPTION_MESSAGE);
        }
        Long serviceId = passwordServiceIdDto.getServiceId();

        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);


        if(passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            throw new ServiceOccupiedException(
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
    public PasswordResponseDto generateAndStorePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, ServiceOccupiedException {
        Long userId = userDto.getId();

        Long serviceId = passwordServiceIdDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);


        if(passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){

            throw new ServiceOccupiedException(
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
    public List<PasswordServiceIdDto> getPasswordList(AuthUserDetailsDto userDetailsDto) {
        List<PasswordEntity> entities = passwordRepository.findAllByUserId(userDetailsDto.getId());
        List<PasswordServiceIdDto> passwords;

        String encryptionKey = userDetailsDto.getEncryptionKey();
        passwords = entities.stream().map(
                entity -> {
                    try {
                        return customPasswordMapper.entityToDecryptedPasswordServiceDto(entity, passwordEncryptor, encryptionKey);
                    } catch (EncryptionException e) {
                        throw new UncheckedEncryptionException(e.getMessage());
                    }
                }
        ).toList();

        return passwords;
    }

    @Override
    public void changePassword(PasswordServiceIdDto passwordServiceIdDto, AuthUserDetailsDto userDto)
            throws NameNotFoundException, EncryptionException, PasswordServiceMappingException {
        Long userId = userDto.getId();

        Long serviceId = passwordServiceIdDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);

        if(!passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            throw new PasswordServiceMappingException(
                    "There is not such a password for %s service"
                            .formatted(serviceEntity.getDomain())
            );
        }

        PasswordEntity passwordEntity = passwordRepository.findByUserIdAndServiceId(userId, serviceId);
        String encryptedPassword;
        try{
            encryptedPassword = passwordEncryptor.encrypt(
                    passwordServiceIdDto.getPassword(),
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

    @Override
    public List<AnalysisDto> analyzeSystem(AuthUserDetailsDto authUser) throws EncryptionException, NameNotFoundException {
        final String encryptionKey = authUser.getEncryptionKey();

        List<PasswordEntity> entities = passwordRepository.findAllByUserId(authUser.getId());

        List<AnalysisDto> analysisDtoList = entities.stream()
                .map(entity -> {
                    try {
                        return customPasswordMapper.entityToAnalysisDto(entity, passwordEncryptor, encryptionKey);
                    } catch (EncryptionException e) {
                        throw new UncheckedEncryptionException(e.getMessage());
                    }
                })
                .toList();

        stringSimilarityUtil.findSimilarities(analysisDtoList, serviceEntityService);

        analysisDtoList
                .forEach(analysisDto -> analysisDto.setStrong(
                        passwordUtil.isPasswordStrong(analysisDto.getPassword()).isStrong()
                    )
                );


        return analysisDtoList;
    }
}
