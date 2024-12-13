package com.hcm.sale_laptop.data.model.other;

public class BannerModel {
    private String product_id;
    private String picture;
    private String title;

    public BannerModel(String product_id, String picture, String title) {
        this.product_id = product_id;
        this.picture = picture;
        this.title = title;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
