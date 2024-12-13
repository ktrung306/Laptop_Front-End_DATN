package com.hcm.sale_laptop.data.model.other;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("review_id")
    private String reviewId;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("order_date")
    private String orderDate;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_title")
    private String productTitle;

    @SerializedName("product_picture")
    private String productPicture;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("comment")
    private String comment;

    @SerializedName("rating")
    private float rating;

    // Constructors
    public ReviewModel() {
    }

    public ReviewModel(String reviewId, String orderId, String orderDate, String productId, String productTitle,
                       String productPicture, String userId, String userName, String comment, float rating) {
        this.reviewId = reviewId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPicture = productPicture;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
