package com.hcm.sale_laptop.data.model.other;

import java.util.List;

public class ProductSaleObject {
    private List<ProductModel> product;

    public ProductSaleObject(List<ProductModel> productModels) {
        this.product = productModels;
    }

    public List<ProductModel> getProductModels() {
        return product;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.product = productModels;
    }
}
