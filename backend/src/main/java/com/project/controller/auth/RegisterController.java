package com.project.controller.auth;

import com.project.user.UserDto;
import com.project.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/registration";
    }

    @PostMapping
    public String register(@ModelAttribute("user") @Valid UserDto userDto,
                           BindingResult bindingResult,
                           Model model){
        return userService.saveUser(userDto,bindingResult,model);
    }

}
