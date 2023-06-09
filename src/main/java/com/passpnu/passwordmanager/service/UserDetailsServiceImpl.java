package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.mapper.UserMapper;
import com.passpnu.passwordmanager.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.userEntityToAuth(
                userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found!")
                )
        );
    }
}
