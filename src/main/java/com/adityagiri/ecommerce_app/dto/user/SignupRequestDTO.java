package com.adityagiri.ecommerce_app.dto.user;

import com.adityagiri.ecommerce_app.enums.Role;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    private String username;
    private String mobile;
    private String password;
    private Role role;
}
