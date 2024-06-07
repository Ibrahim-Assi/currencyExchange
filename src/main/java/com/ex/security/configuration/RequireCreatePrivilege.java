package com.ex.security.configuration;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 8/19/2023
 * Time: 10:43 AM
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
public @interface RequireCreatePrivilege {
}
