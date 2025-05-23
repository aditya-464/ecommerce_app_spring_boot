package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public LoginOrSignupResponseDTO signup(@RequestBody SignupRequestDTO signupRequestDTO){
        return userService.signup(signupRequestDTO);
    }

    @PostMapping("/login")
    public LoginOrSignupResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        return userService.login(loginRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
