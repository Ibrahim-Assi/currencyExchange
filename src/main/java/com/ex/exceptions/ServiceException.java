package com.ex.exceptions;

import lombok.Data;

@Data
public class ServiceException  extends RuntimeException implements ServiceResponse{

    private String messageType;
    public ServiceException(String message) {
        super(message);
        this.messageType = "errorMessage";
    }
}
