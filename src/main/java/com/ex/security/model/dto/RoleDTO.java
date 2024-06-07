package com.ex.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/26/2023
 * Time: 9:33 AM
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO {

    private Long id;
    private String name;
    private String description;
}
