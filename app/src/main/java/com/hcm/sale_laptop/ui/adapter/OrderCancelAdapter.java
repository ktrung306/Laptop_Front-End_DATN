package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.enums.OrderStatus;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.databinding.ItemCancelproductBinding;
import com.hcm.sale_laptop.databinding.ItemConfirmOrderBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class OrderCancelAdapter extends BaseAdapter<OrderStateModel, ItemCancelproductBinding> {

    private final OrderStatus orderStatus;
    private final boolean isHideCheckbox;
    private List<OrderStateModel> itemList;

    public OrderCancelAdapter(List<OrderStateModel> itemList, OnItemClick<OrderStateModel> listener, OrderStatus status, boolean isHideCheckbox) {
        super(itemList, listener);
        this.itemList = itemList;
        this.orderStatus = status;
        this.isHideCheckbox = isHideCheckbox;
    }

    public void handlerRemoveItem(int position) {
        removeItem(position);
        resetCheckbox();
    }

    private void resetCheckbox() {
        for (OrderStateModel model : itemList) {
            model.setSelect(false);
        }
    }

    @Override
    protected ItemCancelproductBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemCancelproductBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(OrderStateModel item, ItemCancelproductBinding binding, int position) {
        binding.tvMaDonHang.setText(item.getId());

    }
}
