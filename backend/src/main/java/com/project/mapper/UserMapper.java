package com.project.mapper;

import com.project.user.UserDto;
import com.project.user.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapUserDtoToUser(UserDto userDto);
}
