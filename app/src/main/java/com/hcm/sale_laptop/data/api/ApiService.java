package com.hcm.sale_laptop.data.api;

import com.hcm.base.BaseResponse;
import com.hcm.sale_laptop.data.model.network.request.ChangePasswordRequest;
import com.hcm.sale_laptop.data.model.network.request.LoginRequest;
import com.hcm.sale_laptop.data.model.network.request.OrderRequest;
import com.hcm.sale_laptop.data.model.network.request.ReviewRequest;
import com.hcm.sale_laptop.data.model.network.request.SignupRequest;
import com.hcm.sale_laptop.data.model.network.response.ApiResponse;
import com.hcm.sale_laptop.data.model.network.response.BannerResponse;
import com.hcm.sale_laptop.data.model.network.response.BrandResponse;
import com.hcm.sale_laptop.data.model.network.response.ChangePassResponse;
import com.hcm.sale_laptop.data.model.network.response.LoginResponse;
import com.hcm.sale_laptop.data.model.network.response.OrderResponse;
import com.hcm.sale_laptop.data.model.network.response.ProductResponse;
import com.hcm.sale_laptop.data.model.network.response.ProductSaleResponse;
import com.hcm.sale_laptop.data.model.network.response.RevenueResponse;
import com.hcm.sale_laptop.data.model.network.response.ReviewResponse;
import com.hcm.sale_laptop.data.model.network.response.ReviewResponse2;
import com.hcm.sale_laptop.data.model.network.response.SignupResponse;
import com.hcm.sale_laptop.data.model.network.response.SoldOrderResponse;
import com.hcm.sale_laptop.data.model.network.response.SoldResponse;
import com.hcm.sale_laptop.data.model.other.ReviewModel;
import com.hcm.sale_laptop.data.model.other.UserModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST(EndPoint.LOGIN)
    Single<LoginResponse> login(@Body LoginRequest signupRequest);

    @POST(EndPoint.SIGNUP)
    Single<SignupResponse> signupUser(@Body SignupRequest signupRequest);

    @POST(EndPoint.FORGOT_PASSWORD)
    Call<ApiResponse> forgotPassword(@Body RequestBody email);

    @GET(EndPoint.ADMIN_CATEGORY)
    Single<BrandResponse> getDataBrand();

    @GET(EndPoint.ADMIN_PRODUCT)
    Single<ProductResponse> getDataProducts();

    @GET(EndPoint.ADMIN_BANNER)
    Single<BannerResponse> getDataBanners();

    @GET(EndPoint.ADMIN_PRODUCT_SALE)
    Single<ProductSaleResponse> getDataProductSales();

    @GET(EndPoint.ADMIN_PRODUCT)
    Single<ProductResponse> searchProducts(@Query("title") String keyWord);

    @GET(EndPoint.ADMIN_PRODUCT)
    Single<ProductResponse> getProductsByBrand(@Query("category_id") String id);

    @POST(EndPoint.ORDERS)
    Single<BaseResponse<Object>> order(@Body OrderRequest request);

    @GET(EndPoint.ORDERS_ALL)
    Single<OrderResponse> getOrderAll();

    @GET(EndPoint.ORDERS_BY_USER)
    Single<OrderResponse> getOrderByUser(@Query("user_id") String id);

    @POST(EndPoint.ORDERSSTATUS)
    Single<BaseResponse<Object>> cancelOrder(
            @Header("Authorization") String token,
            @Query("order_id") String orderId,
            @Query("status") String status,
            @Query("reason") String reason
    );

    @GET(EndPoint.ORDERS_REVIEW)
    Single<OrderResponse> getDataReview();

    @GET(EndPoint.ORDERS_CANCEL)
    Single<OrderResponse> getDataOrdersCancel();

    @POST(EndPoint.REVIEW)
    Single<ReviewResponse2> review(
            @Header("Authorization") String token,
            @Body ReviewRequest request
    );

    @GET(EndPoint.REVIEWS)
    Call<ReviewResponse> getReviews();

    @GET(EndPoint.ORDERS_SOLD)
    Call<SoldResponse> getSoldOrders(
            @Header("Authorization") String token
    );

    @GET(EndPoint.Statistics)
    Call<RevenueResponse> getRevenueStatistics(
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @POST(EndPoint.CHANGE_PASSWORD)
    Call<ChangePassResponse> changePassword(@Body ChangePasswordRequest request);

    @Multipart
    @POST(EndPoint.UPDATE_PROFILE)
    Call<ApiResponse<UserModel>> updateProfile(
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part("gender") RequestBody gender,
            @Part("dateOfBirth") RequestBody dateOfBirth,
            @Part ("image") RequestBody image
    );


}
