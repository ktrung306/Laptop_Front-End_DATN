package com.hcm.sale_laptop.data.model.network.request;

import com.hcm.sale_laptop.data.model.other.ProductOrderModel;

import java.util.List;

public class OrderRequest {
    List<ProductOrderModel> products;
    private String user_id;
    private String address;
    private double total_product_amount;
    private double total_shipping_cost;
    private double product_discount_amount;
    private double total_payment;

    public OrderRequest() {
        super();
    }

    public OrderRequest(String user_id, String address, List<ProductOrderModel> products, float total_product_amount, float total_shipping_cost, float product_discount_amount, float total_payment) {
        this.user_id = user_id;
        this.address = address;
        this.products = products;
        this.total_product_amount = total_product_amount;
        this.total_shipping_cost = total_shipping_cost;
        this.product_discount_amount = product_discount_amount;
        this.total_payment = total_payment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductOrderModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderModel> products) {
        this.products = products;
    }

    public double getTotal_product_amount() {
        return total_product_amount;
    }

    public void setTotal_product_amount(double total_product_amount) {
        this.total_product_amount = total_product_amount;
    }

    public double getTotal_shipping_cost() {
        return total_shipping_cost;
    }

    public void setTotal_shipping_cost(double total_shipping_cost) {
        this.total_shipping_cost = total_shipping_cost;
    }

    public double getProduct_discount_amount() {
        return product_discount_amount;
    }

    public void setProduct_discount_amount(double product_discount_amount) {
        this.product_discount_amount = product_discount_amount;
    }

    public double getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(double total_payment) {
        this.total_payment = total_payment;
    }
}
