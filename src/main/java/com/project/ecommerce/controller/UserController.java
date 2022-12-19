package com.project.ecommerce.controller;

import com.project.ecommerce.dto.user.SignInDto;
import com.project.ecommerce.dto.user.SignInResponseDto;
import com.project.ecommerce.dto.user.SignUpResponseDto;
import com.project.ecommerce.dto.user.SignupDto;
import com.project.ecommerce.exceptions.AuthenticationFailException;
import com.project.ecommerce.exceptions.CustomException;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto sign(@RequestBody SignInDto signInDto) throws AuthenticationFailException, CustomException {
        return userService.signIn(signInDto);
    }
}
