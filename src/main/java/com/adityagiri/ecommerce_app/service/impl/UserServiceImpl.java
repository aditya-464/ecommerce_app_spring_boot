package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.UserRepository;

public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LoginOrSignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        if (userRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByMobile(signupRequestDTO.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        User user = new User();
        user.setMobile(signupRequestDTO.getMobile());
        user.setUsername(signupRequestDTO.getUsername());
        user.setPassword(signupRequestDTO.getPassword());
        user.setRole(signupRequestDTO.getRole());

        User savedUser = userRepository.save(user);

        LoginOrSignupResponseDTO responseDTO = new LoginOrSignupResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setMobile(savedUser.getMobile());
        responseDTO.setUsername(savedUser.getUsername());
        responseDTO.setRole(savedUser.getRole());

        return responseDTO;

    }
}
