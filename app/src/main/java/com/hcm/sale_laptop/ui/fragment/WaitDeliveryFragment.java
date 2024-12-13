package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.data.enums.OrderStatus;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.databinding.FragmentWaitDeliveryBinding;
import com.hcm.sale_laptop.ui.adapter.OrderStateAdapter;
import com.hcm.sale_laptop.ui.viewmodel.WaitingDeliveryViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WaitDeliveryFragment extends BaseFragment<WaitingDeliveryViewModel, FragmentWaitDeliveryBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWaitDeliveryBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        final OrderStateAdapter adapter = new OrderStateAdapter(new ArrayList<>(), null, OrderStatus.ON_DELIVERY, true);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setupAction() {

    }

    @Override
    protected void setupData() {
        mViewModel = new WaitingDeliveryViewModel();

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
            final OrderStateAdapter adapter = (OrderStateAdapter) mBinding.recyclerView.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(orderStateModels)) {
                List<OrderStateModel> list = orderStateModels
                        .stream()
                        .filter(model -> model.getIs_deleted() == 0)
                        .filter(model -> model.getStatus().equals("3"))
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
    }

}
