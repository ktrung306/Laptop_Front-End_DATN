package com.hcm.sale_laptop.data.model.network.response;

import com.google.gson.annotations.SerializedName;
import com.hcm.base.BaseRepository;
import com.hcm.sale_laptop.data.model.other.ReviewModel;

import java.util.List;

public class ReviewResponse extends BaseRepository {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<ReviewModel> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ReviewModel> getData() {
        return data;
    }

    public void setData(List<ReviewModel> data) {
        this.data = data;
    }
}
