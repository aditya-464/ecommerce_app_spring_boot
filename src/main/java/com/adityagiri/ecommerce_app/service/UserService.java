package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupOrUpdateResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.UpdateRequestDTO;

import java.util.List;

public interface UserService {
    List<LoginOrSignupOrUpdateResponseDTO> getAllUsers();
    LoginOrSignupOrUpdateResponseDTO signup(SignupRequestDTO signupRequestDTO);
    LoginOrSignupOrUpdateResponseDTO login(LoginRequestDTO loginRequestDTO);
    String deleteUser(Long id);
    LoginOrSignupOrUpdateResponseDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO);
}
