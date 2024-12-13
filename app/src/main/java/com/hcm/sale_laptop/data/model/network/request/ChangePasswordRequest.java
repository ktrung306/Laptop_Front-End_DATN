package com.hcm.sale_laptop.data.model.network.request;

public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest(String email, String oldPassword, String newPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}



