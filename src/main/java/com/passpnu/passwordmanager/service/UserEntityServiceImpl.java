package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.UserDto;
import com.passpnu.passwordmanager.entity.UserEntity;
import com.passpnu.passwordmanager.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserEntityServiceImpl implements UserEntityService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(UserDto user){
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .encryptionKey(user.getEncryptionKey())
                .role(user.getRole())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return userRepository.save(userEntity);
    }


    @Override
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found!"));
    }

}
