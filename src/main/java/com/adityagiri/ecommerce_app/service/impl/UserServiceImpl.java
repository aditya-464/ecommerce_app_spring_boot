package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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

    public LoginOrSignupResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername());

        if (user==null) {
            throw new RuntimeException("Invalid credentials!");
        }
        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        LoginOrSignupResponseDTO responseDTO = new LoginOrSignupResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setMobile(user.getMobile());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setRole(user.getRole());
        return responseDTO;
    }

    public String deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return "User deleted!";
        }
        else{
            return "User does not exist!";
        }
    }
}
