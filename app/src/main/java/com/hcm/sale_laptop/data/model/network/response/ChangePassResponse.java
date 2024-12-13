package com.hcm.sale_laptop.data.model.network.response;

public class ChangePassResponse {
    private boolean success;
    private String message;

    // Getters and Setters


    public ChangePassResponse() {
    }

    public ChangePassResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}