package com.hcm.sale_laptop.ui.fragment;

import static com.hcm.sale_laptop.utils.Constants.getUserModel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.request.ChangePasswordRequest;
import com.hcm.sale_laptop.data.model.network.response.ChangePassResponse;
import com.hcm.sale_laptop.databinding.FragmentChangePasswordBinding;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;

import retrofit2.Call;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment<BaseViewModel<?>, FragmentChangePasswordBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi(false);

    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
        mBinding.btnPay.setOnClickListener(v -> changePassword());
    }


    private void changePassword() {
        String oldPassword = mBinding.edtOldPass.getText().toString();
        String newPassword = mBinding.edtNewPass.getText().toString();
        String reEnterPassword = mBinding.edtReEnterNewPass.getText().toString();

        // Kiểm tra dữ liệu nhập
        if (oldPassword.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(reEnterPassword)) {
            Toast.makeText(getContext(), "Mật khẩu mới không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo request
        ChangePasswordRequest request = new ChangePasswordRequest(getUserModel().getEmail(), oldPassword, newPassword);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.changePassword(request).enqueue(new retrofit2.Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                if (response.isSuccessful()) {
                    final ChangePassResponse changePassResponse = response.body();
                    if (changePassResponse != null) {
                        if (changePassResponse.isSuccess()) {
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Đổi mật khẩu thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable throwable) {
                Log.e("AdminChangePasswordFragment", "changePassword: " + throwable.getMessage());
            }
        });
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideOrShowBottomNavi(true);
    }

    private void hideOrShowBottomNavi(boolean isShow) {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(isShow);
    }
}
