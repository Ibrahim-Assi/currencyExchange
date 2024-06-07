package com.ex.security.utils;

import com.ex.security.configuration.UserInfoConfig;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/25/2023
 * Time: 10:53 PM
 **/
@Getter
public class AuditInfo {
    private WebAuthenticationDetails getAuthDetails() {
        return (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public static final UserInfoConfig getAuthUser() {
        return (UserInfoConfig) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername() {
        return getAuthUser().getUsername();
    }

}
