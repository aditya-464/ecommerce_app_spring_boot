package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;

public interface UserService {
    LoginOrSignupResponseDTO signup(SignupRequestDTO signupRequestDTO);
    LoginOrSignupResponseDTO login(LoginRequestDTO loginRequestDTO);
}
