package com.hcm.sale_laptop.ui.fragment.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcm.base.BaseActivity;
import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.databinding.FragmentAdminAccountBinding;
import com.hcm.sale_laptop.ui.activity.LoginActivity;
import com.hcm.sale_laptop.ui.fragment.ChangePasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAccountFragment} factory method to
 * create an instance of this fragment.
 */
public class AdminAccountFragment extends BaseFragment<BaseViewModel<?>, FragmentAdminAccountBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminAccountBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    protected void setupAction() {

        setOnClickListener(mBinding.cvChangePassword, view -> {
            addFragment(new AdminChangePasswordFragment(), R.id.fragment_container, true);

        });

        setOnClickListener(mBinding.cvRevenueStatistics, view -> {
            addFragment(new AdminRevenueStatisticsFragment(), R.id.fragment_container, true);

        });

        setOnClickListener(mBinding.cvLogOut, view -> {
            final BaseActivity<BaseViewModel<?>, ?> baseActivity = getBaseActivity();
            if (baseActivity == null) {
                return;
            }
            baseActivity.navigateTo(LoginActivity.class);
            baseActivity.finishActivity();
        });
    }

    @Override
    protected void setupData() {

    }
}