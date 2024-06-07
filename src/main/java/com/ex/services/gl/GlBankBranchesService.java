package com.ex.services.gl;


import com.ex.exceptions.ServiceResponse;
import com.ex.models.dto.gl.GlBankBranchDTO;

import java.util.List;

public interface GlBankBranchesService {

    List<GlBankBranchDTO> getAllBankBranches(Long bankId);
    ServiceResponse addBankBranch(GlBankBranchDTO glBankBranchDTO);
    GlBankBranchDTO getBranchById(Long branchId);
    ServiceResponse deleteBranch(Long branchId) ;
    ServiceResponse updateBranch(GlBankBranchDTO glBankBranchDTO);

}
