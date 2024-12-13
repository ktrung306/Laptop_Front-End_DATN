package com.hcm.sale_laptop.data.enums;

public enum OrderStatus {
    PENDING_CONFIRMATION("CHỜ XÁC NHẬN"),
    AWAITING_DELIVERY("CHỜ NHẬN HÀNG"),
    ON_DELIVERY("ĐANG GIAO"),

    AWAITING_PICKUP("Chờ lấy hàng"),
    CANCELED("Hủy đơn"),
    COMPLETED("Thành công");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
