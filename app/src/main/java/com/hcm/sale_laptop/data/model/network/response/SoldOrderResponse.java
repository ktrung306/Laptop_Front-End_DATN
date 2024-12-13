package com.hcm.sale_laptop.data.model.network.response;

import com.google.gson.annotations.SerializedName;

public class SoldOrderResponse {
    @SerializedName("order_id")
    private String orderId;

    @SerializedName("order_date")
    private String orderDate;

    @SerializedName("product_title")
    private String productTitle;

    @SerializedName("product_picture")
    private String productPicture;

    @SerializedName("product_price")
    private double productPrice;

    @SerializedName("quantity_sold")
    private int quantitySold;

    @SerializedName("customer_name")
    private String customerName;

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public String getCustomerName() {
        return customerName;
    }
}
