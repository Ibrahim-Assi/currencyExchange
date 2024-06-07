package com.ex.security.controllers;

import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.security.model.dto.RoleDTO;
import com.ex.security.services.RoleService;
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
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping(value = "/roleList")
    public String getAllRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "views/roles/rolesList";
    }
    @GetMapping("/createRole")
    public String viewCreateRoleForm(Model model, @ModelAttribute RoleDTO roleDto) {
        model.addAttribute("roleDto", roleDto);
        return "views/roles/createRole";
    }
    @PostMapping(value = "/createRole")
    public String createRole(@ModelAttribute RoleDTO roleDto, RedirectAttributes redirectAttributes) {
        ServiceResponse response = roleService.createRole(roleDto);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/role/roleList";
        }
        else {
            redirectAttributes.addFlashAttribute("roleDto", roleDto);
            return "redirect:/admin/role/createRole";
        }
    }
    @GetMapping("/updateRole/{roleId}")
    public String viewUpdateRole(@PathVariable Long roleId, Model model) {
        RoleDTO roleDto =  roleService.getRoleById(roleId);
        model.addAttribute("roleDto", roleDto);
        return "views/roles/updateRole";
    }
    @PutMapping("/updateRole")
    public String updateCostCenter(@ModelAttribute RoleDTO roleDto, RedirectAttributes redirectAttributes) {
        ServiceResponse response = roleService.updateRole(roleDto.getId(),roleDto);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/role/roleList";
        }
        else {
            return "redirect:/admin/role/updateRole";
        }
    }
    @GetMapping("/deleteRole/{roleId}")
    public String deleteCostCenter(@PathVariable Long roleId, RedirectAttributes redirectAttributes) {
        ServiceResponse response = roleService.deleteRole(roleId);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/role/roleList";
    }
}
