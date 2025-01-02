package com.project.mapper;

import com.project.dto.RegisterDto;
import com.project.entity.Role;
import com.project.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterMapper implements Mapper<RegisterDto, Users>{

    private final BCryptPasswordEncoder passwordEncoder;

    public  Users map(RegisterDto object) {
        Users user = new Users();
        copy(object,user);
        return user;
    }

    public  void copy(RegisterDto object, Users users){
        users.setUsername(object.getUsername());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Role.USER);
    }

}