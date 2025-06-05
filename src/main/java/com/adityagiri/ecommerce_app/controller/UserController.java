package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupOrUpdateResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.UpdateRequestDTO;
import com.adityagiri.ecommerce_app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<LoginOrSignupOrUpdateResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/signup")
    public LoginOrSignupOrUpdateResponseDTO signup(@RequestBody SignupRequestDTO signupRequestDTO){
        return userService.signup(signupRequestDTO);
    }

    @PostMapping("/login")
    public LoginOrSignupOrUpdateResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        return userService.login(loginRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public LoginOrSignupOrUpdateResponseDTO update(@PathVariable Long id, @RequestBody UpdateRequestDTO updateRequestDTO){
        return userService.updateUser(id, updateRequestDTO);
    }

}
