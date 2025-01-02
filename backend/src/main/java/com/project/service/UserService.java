package com.project.service;

import com.project.dto.RegisterDto;
import com.project.entity.Users;
import com.project.mapper.Mapper;
import com.project.mapper.RegisterMapper;
import com.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;

    @Transactional
    public void create(RegisterDto userDto) {
         Optional.of(userDto)
                .map(registerMapper::map)
                .map(userRepository::save)
                .orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(users -> new User(
                        users.getUsername(),
                        users.getPassword(),
                        Collections.singleton(users.getRole())
                ))
                .orElseThrow();
    }


}
