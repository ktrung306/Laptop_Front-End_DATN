package com.hcm.sale_laptop.data.model.network.response;

import com.google.gson.annotations.SerializedName;
import com.hcm.base.BaseRepository;
import com.hcm.sale_laptop.data.model.other.ReviewModel;

import java.util.List;

public class SoldResponse extends BaseRepository {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<SoldOrderResponse> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<SoldOrderResponse> getData() {
        return data;
    }

    public void setData(List<SoldOrderResponse> data) {
        this.data = data;
    }
}
