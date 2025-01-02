package com.project.mapper;

import com.project.dto.RegisterDto;
import com.project.entity.Users;
import org.springframework.stereotype.Component;

@Component
public interface Mapper<F, T> {
    T map(F object);
}
