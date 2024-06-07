package com.ex.services.gl;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.ServiceException;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.models.dto.gl.GlBankBranchDTO;
import com.ex.models.dto.gl.GlBankDTO;
import com.ex.models.entities.gl.GlBankBranches;
import com.ex.models.entities.gl.GlBanks;
import com.ex.repositories.gl.GlBankBranchesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GlBankBranchesServiceImpl implements GlBankBranchesService {

    private final GlBankBranchesRepository glBankBranchesRepository;


    @Override
    public List<GlBankBranchDTO> getAllBankBranches(Long bankId) {
        List<GlBankBranches> branchesList = glBankBranchesRepository.findAllByBankIdOrderByBranchArName(bankId);
        if (ValidateTool.isEmpty(branchesList))
            return null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<GlBanks, GlBankDTO>() {
            @Override
            protected void configure() {
                map().setGlBankBranches(null);
            }
        });

        return branchesList.stream()
                .map(banks -> modelMapper.map(banks, GlBankBranchDTO.class))
                .collect(Collectors.toList());
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ServiceResponse addBankBranch(GlBankBranchDTO glBankBranchDTO) {

        try {
            if(glBankBranchesRepository.findById(glBankBranchDTO.getBranchId()).isPresent())
                throw new ServiceException("رقم الفرع مدخل سابقا");
            if(glBankBranchesRepository.findByBranchArName(glBankBranchDTO.getBranchArName()) !=null || glBankBranchesRepository.findByBranchEnName(glBankBranchDTO.getBranchEnName()) !=null)
                throw new ServiceException("اسم الفرع مدخل سابقا");

            ModelMapper modelMapper = new ModelMapper();
            GlBankBranches glBankBranches = modelMapper.map(glBankBranchDTO, GlBankBranches.class);
            glBankBranchesRepository.save(glBankBranches);
        }
        catch (ServiceException e) {
           return e;
        }
        catch (Exception e) {
           return new ServiceException(e.getMessage());
        }

        return new ServiceResult("تم اضافة الفرع بنجاح");
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public GlBankBranchDTO getBranchById(Long branchId) {
        GlBankBranches glBankBranches  = glBankBranchesRepository.findById(branchId).orElseThrow(() -> new ServiceException("الفرع غير موجود"));
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(glBankBranches , GlBankBranchDTO.class);
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ServiceResponse deleteBranch(Long branchId) {
        GlBankBranches glBankBranches = glBankBranchesRepository.findById(branchId).orElseThrow(() -> new ServiceException("الفرع غير موجود"));
        glBankBranchesRepository.delete(glBankBranches);
        return new ServiceResult("تم حذف الفرع بنجاح");
    }

    //---------------------------------------------------------------------------------------------------
    public ServiceResponse updateBranch(GlBankBranchDTO glBankBranchDTO) {
         glBankBranchesRepository.findById(glBankBranchDTO.getBranchId()).orElseThrow(() -> new ServiceException("الفرع غير موجود"));

        ModelMapper modelMapper = new ModelMapper();
        GlBankBranches glBankBranches = modelMapper.map(glBankBranchDTO, GlBankBranches.class);
        try {
            glBankBranchesRepository.save(glBankBranches);
        }
        catch (Exception ex){
           return new ServiceException("فشلت عملية التعديل");
        }
        return new ServiceResult("تمت عملية التعديل بنجاح");
    }

}




