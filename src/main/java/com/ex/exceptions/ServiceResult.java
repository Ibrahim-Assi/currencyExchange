package com.ex.exceptions;

import lombok.Data;

@Data
public class ServiceResult  implements ServiceResponse{

    private String message;
    private String messageType;

    public ServiceResult(String message) {
       this.message = message;
        this.messageType = "successMessage";
    }
}
