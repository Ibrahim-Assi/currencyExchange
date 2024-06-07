package com.ex.security.controllers;

import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.models.dto.gl.GlCurrenciesDTO;
import com.ex.security.configuration.RequireAdmin;
import com.ex.security.services.RoleService;
import com.ex.services.gl.GlCurrenciesService;
import com.ex.security.model.dto.UserDTO;
import com.ex.security.services.UserService;
import com.ex.security.utils.AuditInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final GlCurrenciesService glCurrenciesService;
    private final RoleService roleService;

    @RequireAdmin
    @GetMapping("/admin/users/usersList")
    public String getAllUsers(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "views/users/usersList";
    }

    @RequireAdmin
    @GetMapping("/admin/users/createUser")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("rolesList", roleService.getAllRoles());
        model.addAttribute("userDTO", new UserDTO());
        return "views/users/createUser";
    }

    @RequireAdmin
    @PostMapping("/admin/users/createUser")
    public String createUser(@ModelAttribute UserDTO userDTO, @RequestParam("centerIds") List<String> centerIds, @RequestParam("roleIds") List<String> rolesIds, RedirectAttributes redirectAttributes) {
        ServiceResponse response = userService.createUser(userDTO, rolesIds, centerIds);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if (response instanceof ServiceResult) {
            return "redirect:/admin/users/usersList";
        }
        return "redirect:/admin/users/createUser";
    }

    @RequireAdmin
    @GetMapping("/admin/users/updateUser/{userId}")
    public String viewUpdateUser(@PathVariable Long userId, Model model) {
        model.addAttribute("userDTO", userService.getUserById(userId));
        model.addAttribute("rolesList", roleService.getAllRoles());
        return "views/users/updateUser";
    }

    @RequireAdmin
    @PostMapping("/admin/users/updateUser")
    public String updateUser(@ModelAttribute UserDTO userDTO, @RequestParam("centerIds") List<String> centerIds, @RequestParam("roleIds") List<String> rolesIds, RedirectAttributes redirectAttributes) {
        ServiceResponse response = userService.updateUser(userDTO, rolesIds, centerIds);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/users/updateUser/" + userDTO.getUserId();
    }

    @RequireAdmin
    @GetMapping("/admin/users/{userId}")
    public String getUser(@PathVariable Long userId, Model model) {
        model.addAttribute("userDto", userService.getUserById(userId));
        return "views/users/userProfile";
    }


    @RequireAdmin
    @GetMapping("/admin/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        ServiceResponse response = userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/users/usersList";
    }

    @GetMapping("/accountant/setIntervalSelected/{id}/{currCode}")
    public String setIntervalSelected(@PathVariable String id, @PathVariable String currCode, HttpServletRequest request) {
        GlCurrenciesDTO mainCurrency = glCurrenciesService.getCurrencyByCurrCode(currCode);
        AuditInfo.getAuthUser().setSelectedIntervalId(id);
        AuditInfo.getAuthUser().setMainCurrency(mainCurrency);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.trim().equals(""))
            return "redirect:" + referer;
        else
            return "redirect:/dashboard";
    }


    /***
     * Change password: used by current authenticated user
     */

    @GetMapping("/changePassword")
    public String viewChangeUserPassword(Model model, @ModelAttribute UserDTO userDTO) {
        model.addAttribute("userDTO", userDTO);
        return "views/changePassword";
    }

    @PostMapping("/changePassword")
    public String updateUserPassword(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        Long userId = AuditInfo.getAuthUser().getId(); // get current authenticated user id
        userDTO.setUserId(userId);
        ServiceResponse response = userService.changeUserPassword(userDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if (response instanceof ServiceResult) {
            return "redirect:/login";
        }
        return "redirect:/changePassword";
    }
}
