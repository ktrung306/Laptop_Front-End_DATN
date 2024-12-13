package com.hcm.sale_laptop.ui.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hcm.base.BaseActivity;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.ApiResponse;
import com.hcm.sale_laptop.databinding.ActivityForgotPasswordBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {

    private ApiService apiService;
    private ActivityForgotPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        apiService = RetrofitClient.getInstance().create(ApiService.class);
        setup();
    }

    @Override
    protected void setupUI() {
        final String registerOrLogin = getString(R.string.register_or_login);
        final String register = getString(R.string.register);
        final String login = getString(R.string.login);

        final SpannableString spannableString = new SpannableString(registerOrLogin);
        setUpSpannableString(spannableString, registerOrLogin, register, R.color.blue_sea, RegisterActivity.class);
        setUpSpannableString(spannableString, registerOrLogin, login, R.color.blue_sky, LoginActivity.class);


    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnLogin, view -> {
            String email = mBinding.editUserName.getText().toString().trim();

            if (email.isEmpty()) {
                showToast("Bạn chưa nhập email");
                return;
            }

            if (!AppUtils.isEmailValid(email)) {
                showToast("Định dạng bạn nhập chưa đúng định dạng email");
                return;
            }

            requestForgotPassword(email);
        });
    }

    @Override
    protected void setupData() {

    }

    private void requestForgotPassword(String email) {
        // Tạo RequestBody từ email
        RequestBody emailBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"email\":\"" + email + "\"}");

        apiService.forgotPassword(emailBody).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    showToast("Đã gửi email thành công!");
                } else {
                    showToast("Không thể gửi email!");
                    Log.e("ForgotPassword", "Lỗi API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showToast("Lỗi kết nối, vui lòng thử lại!");
                Log.e("ForgotPassword", "Lỗi hệ thống: " + t.getMessage());
            }
        });
    }
}
