package com.ex.security.services;

import com.ex.exceptions.ServiceResponse;
import com.ex.security.model.dto.PrivilegeDTO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 9:29 AM
 **/
public interface PrivilegeService {
    PrivilegeDTO getPrivilegeById(Long id);
    PrivilegeDTO findPrivilegeByName(String name);
    List<PrivilegeDTO> getAllPrivileges();
    ServiceResponse createPrivilege(PrivilegeDTO privilegeDto);
    ServiceResponse updatePrivilege(Long id, PrivilegeDTO privilegeDto);
    ServiceResponse deletePrivilege(Long id);
}
