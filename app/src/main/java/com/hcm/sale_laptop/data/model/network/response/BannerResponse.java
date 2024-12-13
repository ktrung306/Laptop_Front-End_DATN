package com.hcm.sale_laptop.data.model.network.response;

import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.model.other.BannerObject;

public class BannerResponse extends BaseResponse<BannerObject> {
    public BannerResponse(boolean success, BannerObject data, Object errors) {
        super(success, data, errors);
    }
}
