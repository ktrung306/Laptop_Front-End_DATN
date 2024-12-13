package com.hcm.sale_laptop.data.repository;

import com.hcm.base.BaseRepository;
import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.request.OrderRequest;

import io.reactivex.rxjava3.core.Single;

public class PaymentsRepository extends BaseRepository {

    private final ApiService apiService;

    public PaymentsRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public Single<BaseResponse<Object>> order(OrderRequest request) {
        return applySingle(apiService.order(request));
    }
}
