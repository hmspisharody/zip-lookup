package com.zip.ziplookup.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException{
    private int status;

    public ApiException (String message, int status) {
        super(message);
        this.status = status;
    }
}
