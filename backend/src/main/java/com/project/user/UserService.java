package com.project.user;

import com.project.config.security.validator.LoginAndPasswordValidator;
import com.project.user.UserDto;
import com.project.user.User;
import com.project.mapper.UserMapper;
import com.project.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginAndPasswordValidator loginAndPasswordValidator;


    public String saveUser(UserDto userDto, BindingResult bindingResult, Model model) {
         loginAndPasswordValidator.validate(userDto,bindingResult);

         if (bindingResult.hasErrors()){
             return "auth/registration";
         }

         User user = userMapper.mapUserDtoToUser(userDto);
         user.setPassword(passwordEncoder.encode(user.getPassword()));

         try {
             userRepository.save(user);
             model.addAttribute("success_registration", true);
         }catch (DataIntegrityViolationException e) {
             log.error("User with such login {} already exists", userDto.getLogin());
             bindingResult.rejectValue("login","", "user with such login already exists");
             return "auth/registration";
         }

         return "auth/registration";
    }


}
