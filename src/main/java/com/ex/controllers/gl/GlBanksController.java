package com.ex.controllers.gl;
import com.ex.common.tools.ReportsTool;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.models.dto.gl.GlBankBranchDTO;
import com.ex.models.dto.gl.GlBankDTO;
import com.ex.services.gl.GlBankBranchesService;
import com.ex.services.gl.GlBanksService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/banks")
public class GlBanksController {

    private final GlBanksService glBanksService;
    private final GlBankBranchesService glBankBranchesService;

    @GetMapping(value = "/banksList")
    public String getAllUniversityCenters(Model model) {
        model.addAttribute("banksList", glBanksService.getAllBanks());
        return "views/banks/banksList";
    }

    //-------------------------------------------------------------------
    @GetMapping("/addBank")
    public String viewAddBank(Model model, @ModelAttribute GlBankDTO glBanks) {
        model.addAttribute("bankDTO", glBanks);
      return "views/banks/createBank";
    }

    @PostMapping("/addBank")
    public String addBank(@ModelAttribute GlBankDTO bank, RedirectAttributes redirectAttributes){
        ServiceResponse response = glBanksService.addBank(bank);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/banks/banksList";
        }
        else {
            redirectAttributes.addFlashAttribute("glBanks", bank);
            return "redirect:/admin/banks/addBank";
        }
    }

    //-------------------------------------------------------------------
    @GetMapping("/updateBank/{bankId}")
    public String editBank(@PathVariable Long bankId,Model model,@ModelAttribute GlBankDTO glBanks) {
        if(glBanks.getBankArName() ==null)
            glBanks = glBanksService.getBankById(bankId);
        model.addAttribute("bankDTO", glBanks);
        return "views/banks/updateBank";
    }

    @PostMapping("/updateBank")
    public String  updateBank(@ModelAttribute GlBankDTO glBankDTO, RedirectAttributes redirectAttributes) {
        ServiceResponse response = glBanksService.updateBank(glBankDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/banks/banksList";
        }
        else {
            redirectAttributes.addFlashAttribute("glBanks", glBankDTO);
            return "redirect:/admin/banks/updateBank";
        }
    }

    //-------------------------------------------------------------------
    @GetMapping("/deleteBank/{bankId}")
    public String deleteBank(@PathVariable Long bankId,RedirectAttributes redirectAttributes) {
        ServiceResponse response = glBanksService.deleteBank(bankId);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/banks/banksList";
    }

    //******************************************************************************************************************

    @GetMapping(value = "/branchList/{bankId}/{branchId}")
    public String getBranchList(@PathVariable Long bankId,@PathVariable Long branchId,Model model) {
        model.addAttribute("bankId", bankId);
        model.addAttribute("branchId", branchId);
        model.addAttribute("banksList", glBanksService.getAllBanks());
        model.addAttribute("branchList", glBankBranchesService.getAllBankBranches(bankId));
        if(branchId !=0)
         model.addAttribute("bankBranchDTO", glBankBranchesService.getBranchById(branchId));
        return "views/banks/branchList";
    }

    //-------------------------------------------------------------------
    @GetMapping("/addBranch/{bankId}")
    public String viewAddBranch(@PathVariable Long bankId, @ModelAttribute GlBankBranchDTO glBankBranchDTO, Model model)   {
        glBankBranchDTO.setBankId(bankId);
        model.addAttribute("banksList", glBanksService.getAllBanks());
        model.addAttribute("bankBranchDTO", glBankBranchDTO );
        return "views/banks/createBranch";
    }

    @PostMapping("/addBranch")
    public String addBranch(@ModelAttribute GlBankBranchDTO glBankBranchDTO,RedirectAttributes redirectAttributes)  {
        ServiceResponse response =  glBankBranchesService.addBankBranch(glBankBranchDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/banks/branchList/"+glBankBranchDTO.getBankId()+"/"+glBankBranchDTO.getBranchId();
        }
        else {
            redirectAttributes.addFlashAttribute("glBankBranchDTO", glBankBranchDTO);
            return "redirect:/views/banks/addBranch/"+glBankBranchDTO.getBankId();
        }
    }

    //-------------------------------------------------------------------
    @GetMapping("/updateBranch/{branchId}")
    public String updateBranch(@PathVariable Long branchId,@ModelAttribute GlBankBranchDTO glBankBranchDTO,Model model)   {
        if(glBankBranchDTO.getBranchArName() ==null)
            glBankBranchDTO = glBankBranchesService.getBranchById(branchId);
        model.addAttribute("bankBranchDTO", glBankBranchDTO);
        model.addAttribute("banksList", glBanksService.getAllBanks());
        return "views/banks/updateBranch";
    }

    @PostMapping("/updateBranch")
    public String updateBranch(@ModelAttribute GlBankBranchDTO glBankBranchDTO,RedirectAttributes redirectAttributes)   {
        ServiceResponse response = glBankBranchesService.updateBranch(glBankBranchDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/banks/branchList/"+glBankBranchDTO.getBankId()+"/"+glBankBranchDTO.getBranchId();
        }
        else {
            redirectAttributes.addFlashAttribute("glBankBranchDTO", glBankBranchDTO);
            return "redirect:/admin/banks/updateBranch/"+glBankBranchDTO.getBranchId();
        }
    }

    //-------------------------------------------------------------------
    @GetMapping("/deleteBranch/{bankId}/{branchId}")
    public String deleteBranch(@PathVariable Long bankId,@PathVariable Long branchId,RedirectAttributes redirectAttributes) {
        ServiceResponse response = glBankBranchesService. deleteBranch(branchId);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult)
            return "redirect:/admin/banks/branchList/"+bankId+"/0";
        else
            return "redirect:/admin/banks/branchList/"+bankId+"/"+branchId;
    }

    @GetMapping("/printBankListReport")
    public ResponseEntity<Resource> generateBankListReport() throws FileNotFoundException, JRException {
        try{
            List<GlBankDTO> banks= glBanksService.getAllBanks();
            return ReportsTool.generateReport(null,banks,"classpath:reports/banks/banksListReport.jrxml","BanksList");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
