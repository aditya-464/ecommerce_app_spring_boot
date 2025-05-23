package com.adityagiri.ecommerce_app.dto.user;

import com.adityagiri.ecommerce_app.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginOrSignupResponseDTO {
    private Long id;
    private String mobile;
    private String username;
    private Role role;
}
