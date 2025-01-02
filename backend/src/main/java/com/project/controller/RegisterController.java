package com.project.controller;

import com.project.dto.RegisterDto;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterDto registerDto,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        userService.create(registerDto);

        return ResponseEntity.ok().build();
    }

}
