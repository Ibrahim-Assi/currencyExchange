package com.ex.security.controllers;

import com.ex.security.utils.AuditInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @RequestMapping("/")
    String showIndex() {
        return "login";
    }

    @GetMapping("/login")
    String showPageIndex() {
        return "login";
    }

//    @PreAuthorize("hasAnyRole('ACCOUNTANT','ADMIN')")
    @GetMapping("/dashboard")
    String showDashboard(Model model) {
        if (AuditInfo.getAuthUser().getRequirePwdChange())
            return "redirect:/changePassword";
        return "views/dashboard";
    }
}
