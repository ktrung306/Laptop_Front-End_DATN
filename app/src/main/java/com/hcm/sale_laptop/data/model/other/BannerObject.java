package com.hcm.sale_laptop.data.model.other;

import java.util.List;

public class BannerObject {
    private List<BannerModel> banners;

    public BannerObject(List<BannerModel> banners) {
        this.banners = banners;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerModel> banners) {
        this.banners = banners;
    }
}
