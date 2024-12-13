package com.hcm.sale_laptop.data.model.network.response;

import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.model.other.OrderObject;

public class OrderResponse extends BaseResponse<OrderObject> {
    public OrderResponse(boolean success, OrderObject data, Object errors) {
        super(success, data, errors);
    }
}
