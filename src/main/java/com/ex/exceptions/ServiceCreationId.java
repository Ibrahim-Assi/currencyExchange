package com.ex.exceptions;

import lombok.Data;

@Data
public class ServiceCreationId implements ServiceResponse{

    private String message;
    private String messageType;
    private Long   id;

    public ServiceCreationId(String message,Long id) {
        this.message = message;
        this.id = id;
        this.messageType = "successMessage";
    }

}
