package com.hcm.sale_laptop.data.model.other;

public class ProductOrderModel {
    private String product_id;
    private int quantity;

    public ProductOrderModel(String product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
