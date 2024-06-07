package com.ex.security.services;


import com.ex.exceptions.ServiceResponse;
import com.ex.security.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    ServiceResponse createUser(UserDTO userDTO, List<String> roleIds, List<String> centerIds);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    UserDTO getUserByEmail(String email);

    ServiceResponse updateUser(UserDTO userDTO, List<String> roleIds, List<String> centerIds);

    ServiceResponse changeUserPassword(UserDTO userDTO);

    ServiceResponse deleteUser(Long userId);
}
