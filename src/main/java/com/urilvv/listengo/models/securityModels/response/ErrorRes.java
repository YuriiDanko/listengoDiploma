package com.urilvv.listengo.models.securityModels.response;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ErrorRes {

    private HttpStatus statusCode;
    private String errorMessage;

    public ErrorRes(HttpStatus statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}