package com.ex.security.services;

import com.ex.exceptions.ServiceResponse;
import com.ex.security.model.dto.RoleDTO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 9:29 AM
 **/
public interface RoleService {
    RoleDTO getRoleById(Long id);
    RoleDTO findByName(String name);
    List<RoleDTO> getAllRoles();
    ServiceResponse createRole(RoleDTO roleDto);
    ServiceResponse updateRole(Long id, RoleDTO roleDto);
    ServiceResponse deleteRole(Long id);
}
