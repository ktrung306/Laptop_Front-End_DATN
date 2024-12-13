package com.hcm.sale_laptop.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.ReviewResponse;
import com.hcm.sale_laptop.data.model.other.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewViewModel extends BaseViewModel<ReviewResponse> {

    private final MutableLiveData<List<ReviewModel>> _reviewList = new MutableLiveData<>();
    public final LiveData<List<ReviewModel>> reviewList = _reviewList;

    private final ApiService apiService;

    public ReviewViewModel(ApiService apiService) {
        this.apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public void fetchReviews() {
        setLoading(true);

        apiService.getReviews().enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ReviewViewModel", "onResponse: " + response.body());
                    _reviewList.postValue(response.body().getData());
                } else {
                    setErrorMessage("Không thể tải danh sách đánh giá.");
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                setLoading(false);
                setErrorMessage("Lỗi: " + t.getMessage());
            }
        });
    }

}
