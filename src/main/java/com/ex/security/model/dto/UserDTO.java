package com.ex.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String oldPassword;
    private String newPassword;
    private String newPasswordMatch;
    private Boolean enabled;
    private Boolean requirePwdChange;
    private Collection<RoleDTO> roles = new HashSet<>();

}
