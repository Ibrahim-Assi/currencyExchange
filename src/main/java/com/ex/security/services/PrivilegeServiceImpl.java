package com.ex.security.services;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.ServiceException;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.security.model.entities.Privilege;
import com.ex.security.model.dto.PrivilegeDTO;
import com.ex.security.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ex.common.constants.AppConstants.*;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 10:30 AM
 **/
@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService{
    private final PrivilegeRepository privilegeRepository;
    private final ModelMapper modelMapper;

    @Override
    public PrivilegeDTO getPrivilegeById(Long id) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() -> null);
        return modelMapper.map(privilege, PrivilegeDTO.class);
    }

    @Override
    public PrivilegeDTO findPrivilegeByName(String name) {
        Privilege privilege = privilegeRepository.findByName(name).orElse(null);
        if (privilege == null)
            return null;
        return modelMapper.map(privilege, PrivilegeDTO.class);
    }

    @Override
    public List<PrivilegeDTO> getAllPrivileges() {
        List<Privilege> privilegeList = privilegeRepository.findAll();
        if (ValidateTool.isEmpty(privilegeList))
            return null;
        return privilegeList.stream().map(privilege ->
                modelMapper.map(privilege, PrivilegeDTO.class)).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADD_PRIVILEGE')")
//    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse createPrivilege(PrivilegeDTO privilegeDto) {
        try {
            Privilege objWithSameName = privilegeRepository.findByName(privilegeDto.getName()).orElse(null);
            Privilege objWithSameId = privilegeRepository.findById(privilegeDto.getId()).orElse(null);
            if (objWithSameName != null) {
                return new ServiceException("Privilege with same name:" + objWithSameName.getName() + " ,is already exists with id:" + objWithSameName.getId());
            } else if (objWithSameId != null) {
                return new ServiceException("Privilege with same id:" + objWithSameId.getId() + " ,is already exists with name:" + objWithSameId.getName());
            }
            privilegeRepository.save(modelMapper.map(privilegeDto, Privilege.class));
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_INSERT_MSG);
    }

    @Override
    public ServiceResponse updatePrivilege(Long id, PrivilegeDTO privilegeDto) {
        try {
            Privilege obj = privilegeRepository.findById(privilegeDto.getId()).orElse(null);
            if (obj == null) {
                return new ServiceException("Privilege is not Exists !!!");
            }
            privilegeRepository.save(modelMapper.map(privilegeDto, Privilege.class));
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_UPDATE_MSG);
    }

    @Override
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    public ServiceResponse deletePrivilege(Long id) {
        try {
            PrivilegeDTO obj = this.getPrivilegeById(id);
            if (obj == null) {
                return new ServiceException("Privilege is not Exists !!!");
            }
            privilegeRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        return new ServiceResult(SUCCESS_DELETE_MSG);
    }
}
