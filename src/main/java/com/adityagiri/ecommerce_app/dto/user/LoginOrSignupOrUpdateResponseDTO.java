package com.adityagiri.ecommerce_app.dto.user;

import com.adityagiri.ecommerce_app.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginOrSignupOrUpdateResponseDTO {
    private Long id;
    private String username;
    private String mobile;
    private Role role;
}
