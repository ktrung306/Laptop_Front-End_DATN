package com.hcm.sale_laptop.data.model.network.response;

public class ApiResponse<T> {
    private boolean success; // Trạng thái thành công hay thất bại
    private String message;  // Tin nhắn từ server
    private T data;          // Dữ liệu trả về (kiểu động)

    // Constructor không tham số
    public ApiResponse() {
    }

    // Constructor có tham số
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter và Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

