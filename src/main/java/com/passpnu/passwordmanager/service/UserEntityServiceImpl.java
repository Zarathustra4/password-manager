package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.UserDto;
import com.passpnu.passwordmanager.entity.Role;
import com.passpnu.passwordmanager.entity.UserEntity;
import com.passpnu.passwordmanager.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;


@Service
@AllArgsConstructor
public class UserEntityServiceImpl implements UserEntityService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(UserDto user){
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .encryptionKey(user.getEncryptionKey())
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return userRepository.save(userEntity);
    }


    @Override
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public Long getIdByUsername(String username) {
        return userRepository.getByUsername(username).getId();
    }

    @Override
    public UserEntity getUserById(Long id) throws NameNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NameNotFoundException("User is not found"));
    }
}
