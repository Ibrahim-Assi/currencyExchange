package com.ex.security.controllers;

import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.security.model.dto.PrivilegeDTO;
import com.ex.security.services.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 10:22 AM
 **/
@Controller
@RequestMapping("/admin/privilege")
@RequiredArgsConstructor
public class PrivilegeController {
    private final PrivilegeService privilegeService;
    //    Privilege Mapping
    @GetMapping(value = "/privilegeList")
    public String getAllPrivilege(Model model) {
        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        return "views/privileges/privilegeList";
    }
    @GetMapping("/createPrivilege")
    public String viewCreatePrivilegeForm(Model model, @ModelAttribute PrivilegeDTO privilegeDto) {
        model.addAttribute("privilegeDto", privilegeDto);
        return "views/privileges/createPrivilege";
    }
    @PostMapping(value = "/createPrivilege")
    public String createPrivilege(@ModelAttribute PrivilegeDTO privilegeDto, RedirectAttributes redirectAttributes) {
        ServiceResponse response = privilegeService.createPrivilege(privilegeDto);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/privilege/privilegeList";
        }
        else {
            redirectAttributes.addFlashAttribute("privilegeService", privilegeService);
            return "redirect:/admin/privilege/createPrivilege";
        }
    }
    @GetMapping("/updatePrivilege/{privilegeId}")
    public String viewUpdatePrivilege(@PathVariable Long privilegeId, Model model) {
        PrivilegeDTO privilegeDto =  privilegeService.getPrivilegeById(privilegeId);
        model.addAttribute("privilegeDto", privilegeDto);
        return "views/privileges/updatePrivilege";
    }
    @PostMapping("/updatePrivilege")
    public String updatePrivilege(@ModelAttribute PrivilegeDTO privilegeDto, RedirectAttributes redirectAttributes) {
        ServiceResponse response = privilegeService.updatePrivilege(privilegeDto.getId(),privilegeDto);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/privilege/privilegeList";
        }
        else {
            return "redirect:/admin/privilege/updatePrivilege";
        }
    }
    @GetMapping("/deletePrivilege/{privilegeId}")
    public String deletePrivilege(@PathVariable Long privilegeId, RedirectAttributes redirectAttributes) {
        ServiceResponse response = privilegeService.deletePrivilege(privilegeId);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/privilege/privilegeList";
    }
}
