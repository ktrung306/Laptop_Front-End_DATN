package com.hcm.sale_laptop.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductModel implements Parcelable, Cloneable {

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
    private String id;
    private String category_id;
    private String title;
    private String slug;
    private String picture;
    private String summary;
    private String description;
    private float price;
    private String created_by;
    private long orderNumber;
    private double totalAmount;
    private int quantity;
    private double sales;
    private String product_id;
    private String order_id;

    public ProductModel(String id, String category_id, String title, String slug,
                        String picture, String summary, String description, float price,
                        String created_by, int quantity, double sales) {
        this.id = id;
        this.category_id = category_id;
        this.title = title;
        this.slug = slug;
        this.picture = picture;
        this.summary = summary;
        this.description = description;
        this.price = price;
        this.created_by = created_by;
        this.quantity = quantity;
        this.sales = sales;
    }

    protected ProductModel(Parcel in) {
        id = in.readString();
        category_id = in.readString();
        title = in.readString();
        slug = in.readString();
        picture = in.readString();
        summary = in.readString();
        description = in.readString();
        price = in.readFloat();
        created_by = in.readString();
    }

    public ProductModel(String id, String category_id, String title, String slug, String picture, String summary, String description, float price, String created_by, long orderNumber, double totalAmount, int quantity, double sales, String product_id, String order_id) {
        this.id = id;
        this.category_id = category_id;
        this.title = title;
        this.slug = slug;
        this.picture = picture;
        this.summary = summary;
        this.description = description;
        this.price = price;
        this.created_by = created_by;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.sales = sales;
        this.product_id = product_id;
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCreatedBy() {
        return created_by;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeValue(this);
    }

    @NonNull
    @Override
    public ProductModel clone() {
        try {
            return (ProductModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
