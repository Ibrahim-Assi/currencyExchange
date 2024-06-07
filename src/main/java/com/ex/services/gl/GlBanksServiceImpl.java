package com.ex.services.gl;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.*;
import com.ex.models.dto.gl.GlBankDTO;
import com.ex.models.entities.gl.GlBanks;
import com.ex.repositories.gl.GlBanksRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GlBanksServiceImpl implements GlBanksService {
    private final GlBanksRepository banksRepository;

    @Override
    public List<GlBankDTO> getAllBanks() {

        List<GlBanks> banksList = banksRepository.findAll(Sort.by( Sort.Order.asc("bankArName")));
        if (ValidateTool.isEmpty(banksList))
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<GlBanks, GlBankDTO>() {
            @Override
            protected void configure() {
                map().setGlBankBranches(null);
            }
        });

        return banksList.stream()
                .map(banks -> modelMapper.map(banks, GlBankDTO.class))
                .collect(Collectors.toList());
    }

    //----------------------------------------------------------------------------------
    @Override
    public ServiceResponse addBank(GlBankDTO glBankDTO) {
        try {
            GlBanks bankDb = banksRepository.findByBankId(glBankDTO.getBankId());
            if(bankDb != null)
                return new ServiceException("البنك تم ادخاله سابقا");

            ModelMapper modelMapper = new ModelMapper();
            GlBanks bank = modelMapper.map(glBankDTO, GlBanks.class);
            banksRepository.save(bank);
        } catch (DataIntegrityViolationException e) {
            return new ServiceException("فشلت عملية اضافة البنك - الاسم مدخل سابقا");
        }
        return new ServiceResult("تم اضافة البنك بنجاح");
    }

    //----------------------------------------------------------------------------------
    @Override
    public ServiceResponse updateBank(GlBankDTO glBankDTO) {

        try {
            banksRepository.findById(glBankDTO.getBankId()).orElseThrow(() -> new ServiceException("البنك المراد تعديله غير موجود"));
            ModelMapper modelMapper = new ModelMapper();
            GlBanks glBanks = modelMapper.map(glBankDTO, GlBanks.class);
            banksRepository.save(glBanks);
        }
        catch (ServiceException ex) {
            return ex;
        }
        catch (Exception ex) {
            return new ServiceException("فشلت عملية تعديل البنك - الاسم مدخل سابقا");
        }
        return new ServiceResult("تم تعديل البنك بنجاح");
    }

    //----------------------------------------------------------------------------------
    @Override
    public GlBankDTO getBankById(Long bankId) {
        GlBanks  glBanks  = banksRepository.findById(bankId).orElseThrow(() -> new ServiceException("البنك المراد تعديله غير موجود"));
        ModelMapper modelMapper = new ModelMapper();
        GlBankDTO glBankDTO = modelMapper.map(glBanks , GlBankDTO.class);
        return glBankDTO;

    }
    //----------------------------------------------------------------------------------
    @Override
    public ServiceResponse deleteBank(Long bankId) {
        GlBanks  glBanks  = banksRepository.findById(bankId).orElseThrow(() -> new ServiceException("البنك المراد حذفه غير موجود"));
        if(glBanks.getGlBankBranches() != null && glBanks.getGlBankBranches().size()>0)
          return new ServiceException("لا يمكن حذف بنك له فروع مدخلة - يجب حذف الفروع اولا");

        banksRepository.delete(glBanks);
        return new ServiceResult("تم حذف البنك بنجاح");
    }
    //----------------------------------------------------------------------------------


}



