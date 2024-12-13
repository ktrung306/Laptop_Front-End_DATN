package com.hcm.sale_laptop.ui.fragment.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.SoldOrderResponse;
import com.hcm.sale_laptop.data.model.network.response.SoldResponse;
import com.hcm.sale_laptop.databinding.FragmentOrderSouldsBinding;
import com.hcm.sale_laptop.ui.adapter.AdminOrderSoldAdapter;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrdersSoldFragment extends Fragment {

    private FragmentOrderSouldsBinding mBinding;
    private AdminOrderSoldAdapter adapter;
    private ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentOrderSouldsBinding.inflate(inflater, container, false);
        apiService = RetrofitClient.getInstance().create(ApiService.class);

        setupUI();
        fetchSoldOrders();
        setupAction();

        return mBinding.getRoot();
    }

    private void setupUI() {
        adapter = new AdminOrderSoldAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(adapter);
    }

    private void setupAction() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::fetchSoldOrders);
    }

    private void fetchSoldOrders() {
        final String token = Constants.getToken();
        apiService.getSoldOrders(token).enqueue(new Callback<SoldResponse>() {
            @Override
            public void onResponse(Call<SoldResponse> call, Response<SoldResponse> response) {
                mBinding.swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body().getData());
                } else {
                    showToast("Không thể tải danh sách đơn hàng đã bán.");
                }
            }

            @Override
            public void onFailure(Call<SoldResponse> call, Throwable t) {
                mBinding.swipeRefreshLayout.setRefreshing(false);
                Log.e("AdminOrdersSoldFragment", "fetchSoldOrders: " + t.getMessage());
            }
        });
    }

    private void onClickOrder(SoldOrderResponse order) {
        showToast("Chọn đơn hàng: " + order.getOrderId());
    }


    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
