package com.hcm.sale_laptop.ui.fragment.manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.RevenueResponse;
import com.hcm.sale_laptop.databinding.FragmentAdminRevenueStatisticsBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRevenueStatisticsFragment extends Fragment {

    private FragmentAdminRevenueStatisticsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAdminRevenueStatisticsBinding.inflate(inflater, container, false);
        setupUI();
        setupActions();
        return mBinding.getRoot();
    }

    private void setupUI() {
        setupDatePickers();
    }

    private void setupActions() {
        mBinding.btnBackArrow.setOnClickListener(v -> requireActivity().onBackPressed());
        mBinding.btnPay.setOnClickListener(v -> fetchRevenueStatistics());
    }

    private void setupDatePickers() {
        mBinding.edtStartDate.setOnClickListener(v -> showDatePickerDialog(mBinding.edtStartDate));
        mBinding.edtEndDate.setOnClickListener(v -> showDatePickerDialog(mBinding.edtEndDate));
    }

    private void showDatePickerDialog(View targetView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                    if (targetView == mBinding.edtStartDate) {
                        mBinding.edtStartDate.setText(date);
                    } else if (targetView == mBinding.edtEndDate) {
                        mBinding.edtEndDate.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void fetchRevenueStatistics() {
        String startDate = mBinding.edtStartDate.getText().toString().trim();
        String endDate = mBinding.edtEndDate.getText().toString().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getRevenueStatistics(startDate, endDate).enqueue(new Callback<RevenueResponse>() {
            @Override
            public void onResponse(Call<RevenueResponse> call, Response<RevenueResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    long totalRevenue = response.body().getTotalRevenue();
                    mBinding.edtDateOfBirth.setText("Doanh thu: " + AppUtils.formatCurrency(totalRevenue));
                } else {
                    Log.e("AdminRevenueStatisticsFragment", "fetchRevenueStatistics: " + response.message());
                    Toast.makeText(requireContext(), "Không thể tải dữ liệu doanh thu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RevenueResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
