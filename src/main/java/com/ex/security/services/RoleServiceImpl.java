package com.ex.security.services;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.ServiceException;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.security.model.entities.Role;
import com.ex.security.model.dto.RoleDTO;
import com.ex.security.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ex.common.constants.AppConstants.*;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 9:43 AM
 **/
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ServiceException("هذه المجموعة غير موجودة"));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null)
            return null;
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        if (ValidateTool.isEmpty(roleList))
            return null;
        return roleList.stream().map(role ->
                modelMapper.map(role, RoleDTO.class)).toList();
    }

    @Override
    public ServiceResponse createRole(RoleDTO roleDto) {
        try {
            Role objWithSameName = roleRepository.findByName(roleDto.getName());
            Role objWithSameId = roleRepository.findById(roleDto.getId()).orElse(null);
            if (objWithSameName != null) {
                return new ServiceException("Role with same name:" + objWithSameName.getName() + " ,is already exists with id:" + objWithSameName.getId());
            } else if (objWithSameId != null) {
                return new ServiceException("Role with same id:" + objWithSameId.getId() + " ,is already exists with name:" + objWithSameId.getName());
            }
            roleRepository.save(modelMapper.map(roleDto, Role.class));
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_INSERT_MSG);
    }

    @Override
    public ServiceResponse updateRole(Long id, RoleDTO roleDto) {
        try {
            Role obj = roleRepository.findById(roleDto.getId()).orElse(null);
            if (obj == null) {
                return new ServiceException("Role is not Exists !!!");
            }
            roleRepository.save(modelMapper.map(roleDto, Role.class));
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_UPDATE_MSG);
    }

    @Override
    public ServiceResponse deleteRole(Long id) {
        try {
            Role obj = roleRepository.findById(id).orElse(null);
            if (obj == null) {
                return new ServiceException("Role is not Exists !!!");
            }
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_DELETE_MSG);
    }
}
