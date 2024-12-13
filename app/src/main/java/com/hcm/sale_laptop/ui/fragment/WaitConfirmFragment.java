package com.hcm.sale_laptop.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hcm.base.BaseFragment;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.enums.OrderStatus;
import com.hcm.sale_laptop.data.model.network.request.CancelOrderRequest;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.databinding.DialogCancelOrderBinding;
import com.hcm.sale_laptop.databinding.FragmentWaitConfirmBinding;
import com.hcm.sale_laptop.ui.adapter.OrderStateAdapter;
import com.hcm.sale_laptop.ui.viewmodel.OrderViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WaitConfirmFragment extends BaseFragment<OrderViewModel, FragmentWaitConfirmBinding> implements OnItemClick<OrderStateModel> {

    private OrderStateModel orderStateModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWaitConfirmBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        final OrderStateAdapter adapter = new OrderStateAdapter(new ArrayList<>(), this, OrderStatus.PENDING_CONFIRMATION, false);
        mBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnCancelOrder, view -> {
            if (orderStateModel == null) {
                showToast("Bạn chưa chọn đơn hàng nào để hủy");
                return;
            }
            showDialogWarning();
        });
    }

    private void showDialogWarning() {
        final DialogCancelOrderBinding binding = DialogCancelOrderBinding.inflate(this.getLayoutInflater());

        // Tạo dialog
        final Dialog dialog = new Dialog(requireContext(), R.style.CustomDialogTheme);

        dialog.setContentView(binding.getRoot());
        binding.btnExit.setActivated(true);
        binding.btnCancelOrder.setOnClickListener(v -> {
            final String reason = binding.etReason.getText().toString().trim();
            final CancelOrderRequest request = new CancelOrderRequest(orderStateModel.getId(), reason,"4");
            mViewModel.cancelOrder(request);
            dialog.dismiss();
        });

        binding.btnExit.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
            final OrderStateAdapter adapter = (OrderStateAdapter) mBinding.recyclerView.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(orderStateModels)) {
                List<OrderStateModel> list = orderStateModels
                        .stream()
                        .filter(model -> model.getIs_deleted() == 0)
                        .filter(model -> model.getStatus().equals("1"))
                        .collect(Collectors.toList());
                adapter.setItems(list);
            }
        });

        mViewModel.getIsCancelOrderSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                final OrderStateAdapter adapter = (OrderStateAdapter) mBinding.recyclerView.getAdapter();
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
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(OrderStateModel model) {
        this.orderStateModel = model.isSelect() ? model : null;
    }
}