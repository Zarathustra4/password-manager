package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.user.ChangeRoleDto;
import com.passpnu.passwordmanager.dto.user.UserDto;
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
    public void saveUser(UserDto user){
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .encryptionKey(user.getEncryptionKey())
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(userEntity);
    }

    @Override
    public void saveAdmin(UserDto user) {
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .encryptionKey(user.getEncryptionKey())
                .role(Role.ROLE_ADMIN)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(userEntity);
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

    @Override
    public void changeRole(ChangeRoleDto changeRoleDto) throws NameNotFoundException {
        String username = changeRoleDto.getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(
                        () -> new NameNotFoundException("There is no such a user with username: %s"
                        .formatted(username))
                );
        userEntity.setRole(changeRoleDto.getRole());
        userRepository.save(userEntity);
    }
}
