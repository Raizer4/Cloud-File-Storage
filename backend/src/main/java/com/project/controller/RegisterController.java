package com.project.controller;

import com.project.dto.RegisterDto;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Void> register(@ModelAttribute @Validated RegisterDto registerDto,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        userService.create(registerDto);

        return ResponseEntity.ok().build();
    }

}
