package com.ex.services.gl;

import com.ex.exceptions.ServiceResponse;
import com.ex.models.dto.gl.GlBankDTO;

import java.util.List;

public interface GlBanksService {
    List<GlBankDTO>   getAllBanks();
    ServiceResponse addBank(GlBankDTO glBankDTO);
    ServiceResponse updateBank(GlBankDTO banksDTO);
    ServiceResponse deleteBank(Long bankId);
    GlBankDTO getBankById(Long bankId);
}
