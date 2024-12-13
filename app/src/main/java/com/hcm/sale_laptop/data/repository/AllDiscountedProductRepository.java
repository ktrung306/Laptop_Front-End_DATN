package com.hcm.sale_laptop.data.repository;

import com.hcm.base.BaseRepository;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.ProductSaleResponse;

import io.reactivex.rxjava3.core.Single;

public class AllDiscountedProductRepository extends BaseRepository {

    private final ApiService apiService;

    public AllDiscountedProductRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public Single<ProductSaleResponse> getDataProductSales() {
        return applySingle(apiService.getDataProductSales());
    }
}
