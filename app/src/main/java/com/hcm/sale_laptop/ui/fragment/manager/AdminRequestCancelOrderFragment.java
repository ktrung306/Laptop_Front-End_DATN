package com.hcm.sale_laptop.ui.fragment.manager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hcm.base.BaseFragment;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.network.request.CancelOrderRequest;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.databinding.DialogCancelOrderBinding;
import com.hcm.sale_laptop.databinding.FragmentAdminRequestCancelOrderBinding;
import com.hcm.sale_laptop.ui.adapter.RequestCancelOrderAdapter;
import com.hcm.sale_laptop.ui.viewmodel.AdminRequestCancelOrderViewModel;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdminRequestCancelOrderFragment extends BaseFragment<AdminRequestCancelOrderViewModel, FragmentAdminRequestCancelOrderBinding> implements OnItemClick<OrderStateModel> {

    private OrderStateModel orderStateModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminRequestCancelOrderBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {

        final RequestCancelOrderAdapter adapter = new RequestCancelOrderAdapter(new ArrayList<>(), this);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnConfirmOrder, view -> {
            if (orderStateModel == null) {
                showToast("Bạn chưa chọn đơn hàng nào để hủy");
                return;
            }
            showDialogWarning();
        });
    }

    private void showDialogWarning() {
        final CancelOrderRequest request = new CancelOrderRequest( orderStateModel.getId(), "","5");
        request.setOrder_id(orderStateModel.getId());
        mViewModel.cancelOrder(request);
    }

    @Override
    protected void setupData() {
        mViewModel = new AdminRequestCancelOrderViewModel();

        mViewModel.getDataOrdersCancel();

        mViewModel.errorMessage.observe(this, this::showToast);

        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getOrderData().observe(this, orderStateModels -> {
            final RequestCancelOrderAdapter adapter = (RequestCancelOrderAdapter) mBinding.recyclerView.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(orderStateModels)) {
                List<OrderStateModel> list = orderStateModels
                        .stream()
                        .filter(model -> model.getIs_deleted() == 0)
                        .filter(model -> model.getStatus().equals("4"))
                        .collect(Collectors.toList());
                adapter.setItems(list);
            }
        });

        mViewModel.getIsConfirmOrderSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                final RequestCancelOrderAdapter adapter = (RequestCancelOrderAdapter) mBinding.recyclerView.getAdapter();
                if (adapter != null) {
                    adapter.handlerRemoveItem(orderStateModel.getPosition());
                    orderStateModel = null;
                }
                showToast("Hủy đơn hàng thành công");
            } else {
                showToast("Hủy đơn hàng thất bại");
            }
        });
    }

    @Override
    public void onClick(OrderStateModel model) {
        this.orderStateModel = model.isSelect() ? model : null;
    }
}