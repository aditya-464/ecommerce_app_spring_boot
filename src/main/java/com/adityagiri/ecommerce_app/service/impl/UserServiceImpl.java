package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.user.LoginOrSignupOrUpdateResponseDTO;
import com.adityagiri.ecommerce_app.dto.user.LoginRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.SignupRequestDTO;
import com.adityagiri.ecommerce_app.dto.user.UpdateRequestDTO;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<LoginOrSignupOrUpdateResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<LoginOrSignupOrUpdateResponseDTO> usersList = new ArrayList<>();
        for(User user: users){
            LoginOrSignupOrUpdateResponseDTO responseDTO = new LoginOrSignupOrUpdateResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setMobile(user.getMobile());
            responseDTO.setUsername(user.getUsername());
            responseDTO.setRole(user.getRole());
            usersList.add(responseDTO);
        }

        return usersList;
    }

    public LoginOrSignupOrUpdateResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        if(signupRequestDTO.getMobile()==null || signupRequestDTO.getUsername()==null || signupRequestDTO.getPassword()==null || signupRequestDTO.getRole()==null){
            throw new RuntimeException("All fields are mandatory!");
        }

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

        LoginOrSignupOrUpdateResponseDTO responseDTO = new LoginOrSignupOrUpdateResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setMobile(savedUser.getMobile());
        responseDTO.setUsername(savedUser.getUsername());
        responseDTO.setRole(savedUser.getRole());
        return responseDTO;
    }

    public LoginOrSignupOrUpdateResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername());

        if (user==null) {
            throw new RuntimeException("Invalid credentials!");
        }
        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        LoginOrSignupOrUpdateResponseDTO responseDTO = new LoginOrSignupOrUpdateResponseDTO();
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

    public LoginOrSignupOrUpdateResponseDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if(updateRequestDTO.getUsername()!=null && !updateRequestDTO.getUsername().equals(user.getUsername())){
            if(userRepository.existsByUsername(updateRequestDTO.getUsername())){
                throw new RuntimeException("Username already exists!");
            }
            user.setUsername(updateRequestDTO.getUsername());
        }

        if(updateRequestDTO.getPassword()!=null && !updateRequestDTO.getPassword().equals(user.getPassword())){
            user.setPassword(updateRequestDTO.getPassword());
        }

        if(updateRequestDTO.getMobile()!=null && !updateRequestDTO.getMobile().equals(user.getMobile())){
            if (userRepository.existsByMobile(updateRequestDTO.getMobile())) {
                throw new RuntimeException("Mobile already exists!");
            }
            user.setMobile(updateRequestDTO.getMobile());
        }

        if(updateRequestDTO.getRole()!=null && !updateRequestDTO.getRole().equals(user.getRole())){
            user.setRole(updateRequestDTO.getRole());
        }

        User updatedUser = userRepository.save(user);

        return new LoginOrSignupOrUpdateResponseDTO(updatedUser.getId(),updatedUser.getUsername(),updatedUser.getMobile(),updatedUser.getRole());
    }
}
