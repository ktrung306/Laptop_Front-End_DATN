package com.hcm.sale_laptop.data.model.network.response;

import com.google.gson.annotations.SerializedName;

public class RevenueResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private RevenueData data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RevenueData getData() {
        return data;
    }

    public void setData(RevenueData data) {
        this.data = data;
    }

    public long getTotalRevenue() {
        return data != null ? data.getTotalRevenue() : 0;
    }

    public static class RevenueData {
        @SerializedName("totalRevenue")
        private long totalRevenue;

        public long getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(long totalRevenue) {
            this.totalRevenue = totalRevenue;
        }
    }
}
