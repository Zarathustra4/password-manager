package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.SimilarPasswordDto;
import com.passpnu.passwordmanager.dto.password.PasswordDto;
import com.passpnu.passwordmanager.dto.password.PasswordRequestDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.password.PasswordResponseDto;
import com.passpnu.passwordmanager.encrypt.PasswordEncryptor;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;

import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.exception.PasswordServiceMappingException;
import com.passpnu.passwordmanager.generator.StringPasswordGenerator;

import com.passpnu.passwordmanager.mapper.CustomPasswordMapper;
import com.passpnu.passwordmanager.mapper.PasswordMapper;
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
import java.util.ArrayList;

import java.util.List;


@Service
@AllArgsConstructor
public class PasswordEntityServiceImpl implements PasswordEntityService{

    private final PasswordEncryptor passwordEncryptor;
    private final StringPasswordGenerator passwordGenerator;
    private final PasswordRepository passwordRepository;
    private final PasswordMapper passwordMapper;
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
            throws NameNotFoundException, EncryptionException, PasswordServiceMappingException {
        Long userId = userDto.getId();

        Long serviceId = passwordRequestDto.getServiceId();
        ServiceEntity serviceEntity = serviceEntityService.getEntityById(serviceId);

        if(!passwordRepository.existsByUserIdAndServiceId(userId, serviceId)){
            //change to relevant exception
            throw new PasswordServiceMappingException(
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

    @Override
    public List<AnalysisDto> analyzeSystem(AuthUserDetailsDto authUser) throws EncryptionException, NameNotFoundException {
        List<AnalysisDto> analysis = new ArrayList<>();

        List<PasswordEntity> entities = passwordRepository.findAllByUserId(authUser.getId());
        List<PasswordDto> passwordDtoList = new ArrayList<>();
        for(PasswordEntity entity : entities){
            passwordDtoList.add(
                    customPasswordMapper.entityToDecryptedDto(entity, passwordEncryptor, authUser.getEncryptionKey())
            );
        }

        final int passwordDtoListSize = passwordDtoList.size();
        String serviceName;
        PasswordDto passwordDto1;
        PasswordDto passwordDto2;
        PasswordTestAnswer passwordTestAnswer;
        AnalysisDto analysisDto;
        double similarity;

        for(int i = 0; i < passwordDtoListSize; i++){
            passwordDto1 = passwordDtoList.get(i);
            serviceName = serviceEntityService.getEntityById(passwordDto1.getServiceId()).getTitle();
            passwordTestAnswer = checkPasswordStrength(passwordDto1.getPassword());
            analysisDto = AnalysisDto.builder()
                    .serviceName(serviceName)
                    .similarPasswords(new ArrayList<>())
                    .isStrong(passwordTestAnswer.isStrong())
                    .build();

            analysis.add(analysisDto);
            for(int j = 0; j < passwordDtoListSize; j++){
                if(i == j){
                    continue;
                }
                passwordDto2 = passwordDtoList.get(j);
                similarity = stringSimilarityUtil.calculateSimilarity(
                        passwordDto1.getPassword(),
                        passwordDto2.getPassword());
                analysisDto.addSimilarPassword(
                        SimilarPasswordDto.builder()
                                .similarity(similarity)
                                .serviceDomain(serviceEntityService.getEntityById(passwordDto2.getServiceId()).getTitle())
                                .serviceDomain(serviceEntityService.getEntityById(passwordDto2.getServiceId()).getDomain())
                                .password(passwordDto2.getPassword())
                                .build()
                );
            }
            analysis.add(analysisDto);
        }

        return analysis;
    }
}
