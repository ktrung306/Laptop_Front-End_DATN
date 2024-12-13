package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.enums.OrderStatus;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.databinding.FragmentOrderCancelledBinding;
import com.hcm.sale_laptop.ui.adapter.OrderStateAdapter;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.ui.viewmodel.OrderViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCancelledFragment extends BaseFragment<OrderViewModel, FragmentOrderCancelledBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentOrderCancelledBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi(false);
        final OrderStateAdapter adapter = new OrderStateAdapter(new ArrayList<>(), null, OrderStatus.CANCELED, true);
        mBinding.rvOrderCancelled.setAdapter(adapter);
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
    }

    @Override
    protected void setupData() {
        mViewModel = new OrderViewModel();

        mViewModel.getOrderByUser(Constants.getUserModel().getId());

        mViewModel.errorMessage.observe(this, this::showToast);

        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getOrderData().observe(this, orderStateModels -> {
            final OrderStateAdapter adapter = (OrderStateAdapter) mBinding.rvOrderCancelled.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(orderStateModels)) {
                List<OrderStateModel> list = orderStateModels
                        .stream()
                        .filter(model -> model.getIs_deleted() == 1)
                        .filter(model -> model.getStatus().equals("4"))
                        .collect(Collectors.toList());
                adapter.setItems(list);
            }
        });

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
