package com.hcm.sale_laptop.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BrandModel implements Parcelable {
    public static final Creator<BrandModel> CREATOR = new Creator<BrandModel>() {
        @Override
        public BrandModel createFromParcel(Parcel in) {
            return new BrandModel(in);
        }

        @Override
        public BrandModel[] newArray(int size) {
            return new BrandModel[size];
        }
    };
    private String name;
    private String parent_category;
    private String slug;
    private String description;
    private String id;
    private String picture;

    public BrandModel(String name, String parent_category, String slug, String description, String id, String picture) {
        this.name = name;
        this.parent_category = parent_category;
        this.slug = slug;
        this.description = description;
        this.id = id;
        this.picture = picture;
    }

    protected BrandModel(Parcel in) {
        name = in.readString();
        parent_category = in.readString();
        slug = in.readString();
        description = in.readString();
        id = in.readString();
        picture = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_category() {
        return parent_category;
    }

    public void setParent_category(String parent_category) {
        this.parent_category = parent_category;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(parent_category);
        parcel.writeString(slug);
        parcel.writeString(description);
        parcel.writeString(id);
        parcel.writeString(picture);
    }
}
