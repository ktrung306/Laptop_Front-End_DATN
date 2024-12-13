package com.hcm.sale_laptop.ui.activity;

import android.os.Bundle;

import com.hcm.base.BaseActivity;
import com.hcm.sale_laptop.databinding.ActivityNewPasswordBinding;
import com.hcm.sale_laptop.ui.viewmodel.NewPasswordActivityViewModel;

public class NewPasswordActivity extends BaseActivity<NewPasswordActivityViewModel, ActivityNewPasswordBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setup();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnSave, view -> {
            final String pass = mBinding.editEnterNewPass.getText().toString();
            final String reEnterPass = mBinding.editEnterNewPass.getText().toString();
            if (pass.isEmpty() || reEnterPass.isEmpty()) {
                showToast("Bạn chưa nhập mật khẩu");
                return;
            }

            mViewModel.requestApiValidEmail(pass);
        });
        setOnClickListener(mBinding.btnLogin, view -> navigateTo(LoginActivity.class));
    }

    @Override
    protected void setupData() {
        mViewModel = new NewPasswordActivityViewModel();
    }

}