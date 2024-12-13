package com.hcm.sale_laptop.ui.fragment.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.databinding.FragmentAdminPurchaseManagerBinding;
import com.hcm.sale_laptop.ui.adapter.ViewPagerStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPurchaseManagerFragment} factory method to
 * create an instance of this fragment.
 */
public class AdminPurchaseManagerFragment extends BaseFragment<BaseViewModel<?>, FragmentAdminPurchaseManagerBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminPurchaseManagerBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AdminOrderConfirmFragment());
        fragments.add(new AdminRequestCancelOrderFragment());
        fragments.add(new AdminSeeReviewFragment());

        final ViewPagerStateAdapter viewPagerStateAdapter = new ViewPagerStateAdapter(this, fragments);
        mBinding.viewPager.setAdapter(viewPagerStateAdapter);
        mBinding.viewPager.setUserInputEnabled(false);

    }

    private List<String> getDataTitles() {
        final List<String> titles = new ArrayList<>();
        titles.add("Xác nhận đơn hàng");
        titles.add("Yêu cầu hủy hàng");
        titles.add("Đánh giá");
        return titles;
    }

    @Override
    protected void setupAction() {
        mBinding.tab1.setOnClickListener(view -> updateViewPager(0));
        mBinding.tab2.setOnClickListener(view -> updateViewPager(1));
        mBinding.tab3.setOnClickListener(view -> updateViewPager(2));
    }

    private void updateViewPager(int tab) {
        final int blackColor = getResources().getColor(R.color.black, null);
        final int redColor = getResources().getColor(com.hcm.base.R.color.red, null);

        mBinding.tvTab1.setTextColor(blackColor);
        mBinding.tvTab2.setTextColor(blackColor);
        mBinding.tvTab3.setTextColor(blackColor);

        switch (tab) {
            case 0:
                mBinding.tvTab1.setTextColor(redColor);
                break;
            case 1:
                mBinding.tvTab2.setTextColor(redColor);
                break;
            case 2:
                mBinding.tvTab3.setTextColor(redColor);
                break;
        }

        mBinding.txtHeader.setText(getDataTitles().get(tab));
        mBinding.viewPager.setCurrentItem(tab);
    }

    @Override
    protected void setupData() {

    }

}