package com.hcm.sale_laptop.data.model.network.response;

import com.google.gson.annotations.SerializedName;
import com.hcm.base.BaseRepository;
import com.hcm.sale_laptop.data.model.other.ReviewModel;

import java.util.List;

public class ReviewResponse2 extends BaseRepository {

    @SerializedName("success")
    private boolean success;

    @SerializedName("reviewId")
    private String reviewId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return reviewId;
    }

    public void setData(String reviewId) {
        this.reviewId = reviewId;
    }
}
