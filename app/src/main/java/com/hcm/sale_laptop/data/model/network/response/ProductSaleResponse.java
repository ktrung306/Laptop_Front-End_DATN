package com.hcm.sale_laptop.data.model.network.response;

import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.model.other.ProductSaleObject;

public class ProductSaleResponse extends BaseResponse<ProductSaleObject> {
    public ProductSaleResponse(boolean success, ProductSaleObject data, Object errors) {
        super(success, data, errors);
    }
}
