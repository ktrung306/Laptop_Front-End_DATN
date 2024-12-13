package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemRequestCancelOrderBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class RequestCancelOrderAdapter extends BaseAdapter<OrderStateModel, ItemRequestCancelOrderBinding> {

    private List<OrderStateModel> itemList;

    public RequestCancelOrderAdapter(List<OrderStateModel> itemList, OnItemClick<OrderStateModel> listener) {
        super(itemList, listener);
        this.itemList = itemList;
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
    protected ItemRequestCancelOrderBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemRequestCancelOrderBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(OrderStateModel item, ItemRequestCancelOrderBinding binding, int position) {
        binding.tvAddress.setText(item.getAddress());

        binding.tvMaDonHang.setText(item.getId());
        binding.rvProduct.setAdapter(new ItemProductAdapter(item.getProducts(), null));
        binding.tvTongTienHang.setText(AppUtils.customPrice(item.getPrices_order()));
        binding.checkbox.setChecked(item.isSelect());
        binding.tvTienGiamGiaSanPham.setText(AppUtils.customPrice(getDiscountAmount(item.getProducts())));
        binding.tvTongTienPhiVanChuyen.setText(AppUtils.customPrice(getShippingCost(item.getProducts())));
        binding.tvTongTienThanhToan.setText(AppUtils.customPrice(item.getPrices_order() - getDiscountAmount(item.getProducts()) + getShippingCost(item.getProducts())));

        binding.tvReason.setText(item.getReason());

        binding.checkbox.setOnClickListener(view -> {
            itemList = this.getItemList();

            for (OrderStateModel model : itemList) {
                model.setSelect(false);
            }

            item.setSelect(binding.checkbox.isChecked());

            binding.getRoot().post(this::notifyDataSetChanged);
            final OnItemClick<OrderStateModel> onItemClick = getListener();
            if (onItemClick != null) {
                item.setPosition(position);
                onItemClick.onClick(item);
            }
        });
    }

    private double getDiscountAmount(List<ProductModel> itemList) {
        return getOrderNumber(itemList) * 150_000L;
    }

    private double getShippingCost(List<ProductModel> itemList) {
        return getOrderNumber(itemList) * 10_000L;
    }

    private long getOrderNumber(List<ProductModel> itemList) {
        long number = 0;
        for (ProductModel model : itemList) {
            number += model.getQuantity();
        }
        return number;
    }
}
