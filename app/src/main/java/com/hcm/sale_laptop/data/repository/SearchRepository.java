package com.hcm.sale_laptop.data.repository;

import com.hcm.base.BaseRepository;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.ProductResponse;

import io.reactivex.rxjava3.core.Single;

public class SearchRepository extends BaseRepository {

    private final ApiService apiService;

    public SearchRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public Single<ProductResponse> getDataProducts() {
        return applySingle(apiService.getDataProducts());
    }

    public Single<ProductResponse> searchProducts(String keyWord) {
        return applySingle(apiService.searchProducts(keyWord));
    }
}
