package com.hcm.sale_laptop.utils;

import com.hcm.sale_laptop.data.model.other.UserModel;

public class Constants {
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String DATE_FORMAT_VN = "dd/MM/yyyy";
    public static final String TIME_FORMAT_VN = "dd/MM/yyyy HH:mm:ss";
    public static final String KEY_PRODUCT_MODEL = "KEY_PRODUCT_MODEL";
    public static final String KEY_BRAND_MODEL = "KEY_BRAND_MODEL";
    public static final String KEY_TOTAL_AMOUNT = "KEY_TOTAL_AMOUNT";
    public static final String KEY_SELECTED_PRODUCTS = "KEY_SELECTED_PRODUCTS";
    private static UserModel userModel;
    private static String token;

    public static UserModel getUserModel() {
        if (userModel == null) {
            userModel = new UserModel();
        }
        return userModel;
    }

    public static void setUserModel(UserModel model) {
        userModel = model;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String value) {
        token = value;
    }
}
