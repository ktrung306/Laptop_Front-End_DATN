package com.hcm.sale_laptop.data.repository;


import com.hcm.base.BaseRepository;
import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.request.CancelOrderRequest;
import com.hcm.sale_laptop.data.model.network.request.ReviewRequest;
import com.hcm.sale_laptop.data.model.network.response.OrderResponse;
import com.hcm.sale_laptop.data.model.network.response.ReviewResponse;
import com.hcm.sale_laptop.data.model.network.response.ReviewResponse2;
import com.hcm.sale_laptop.utils.Constants;

import io.reactivex.rxjava3.core.Single;

public class OrderRepository extends BaseRepository {

    private final ApiService apiService;

    public OrderRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public Single<OrderResponse> getOrderByUser(String id) {
        return applySingle(apiService.getOrderByUser(id));
    }

    public Single<BaseResponse<Object>> cancelOrder(CancelOrderRequest request) {
        final String token = Constants.getToken();
        return applySingle(apiService.cancelOrder(token, request.getOrder_id(),request.getStatus(),request.getReason()));
    }

    public Single<ReviewResponse2> reviewProduct(ReviewRequest request) {
        final String token = Constants.getToken();
        return applySingle(apiService.review(token,request));
    }


    public Single<OrderResponse> getDataReview() {
        return applySingle(apiService.getDataReview());
    }

    public Single<OrderResponse> getOrderAll() {
        return applySingle(apiService.getOrderAll());
    }

    public Single<OrderResponse> getDataOrdersCancel() {
        return applySingle(apiService.getDataOrdersCancel());
    }
}
