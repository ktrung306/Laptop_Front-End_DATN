package com.hcm.sale_laptop.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.base.BaseActivity;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.databinding.ActivityMainAdminBinding;
import com.hcm.sale_laptop.ui.fragment.manager.AdminAccountFragment;
import com.hcm.sale_laptop.ui.fragment.manager.AdminOrdersSoldFragment;
import com.hcm.sale_laptop.ui.fragment.manager.AdminPurchaseManagerFragment;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.ui.viewmodel.factory.MainActivityViewModelFactory;

public class AdminActivity extends BaseActivity<MainActivityViewModel, ActivityMainAdminBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new AdminPurchaseManagerFragment());
        }
        setup();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    protected void setupAction() {
        mBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            final int id = item.getItemId();
            if (id == R.id.nav_manager) {
                fragment = new AdminPurchaseManagerFragment();
            }
            if (id == R.id.nav_orders) {
                fragment = new AdminOrdersSoldFragment();
            }
            if (id == R.id.nav_account) {
                fragment = new AdminAccountFragment();
            }
            loadFragment(fragment);
            return true;
        });
    }

    @Override
    protected void setupData() {
        mViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getApplication()))
                .get(MainActivityViewModel.class);
        mViewModel.isBottomNavVisible().observe(this, isVisible -> {
            if (isVisible) {
                mBinding.bottomNavigation.setVisibility(View.VISIBLE);
            } else {
                mBinding.bottomNavigation.setVisibility(View.GONE);
            }
        });
    }

    // Phương thức để load fragment
    private void loadFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}