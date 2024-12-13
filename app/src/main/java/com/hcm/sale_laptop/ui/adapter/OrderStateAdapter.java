package com.hcm.sale_laptop.ui.adapter;

import static com.hcm.sale_laptop.ui.viewmodel.HomeFragmentViewModel.ListProducts;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.enums.OrderStatus;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemConfirmOrderBinding;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.CartManager;

import java.util.List;

public class OrderStateAdapter extends BaseAdapter<OrderStateModel, ItemConfirmOrderBinding> {

    private final OrderStatus orderStatus;
    private final boolean isHideCheckbox;
    private List<OrderStateModel> itemList;

    public OrderStateAdapter(List<OrderStateModel> itemList, OnItemClick<OrderStateModel> listener, OrderStatus status, boolean isHideCheckbox) {
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
    protected ItemConfirmOrderBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemConfirmOrderBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(OrderStateModel item, ItemConfirmOrderBinding binding, int position) {
        binding.tvAddress.setText(item.getAddress());
        binding.tvTrangThai.setText(orderStatus.getDescription());
        binding.tvMaDonHang.setText(item.getId());
        binding.rvProduct.setAdapter(new ItemProductAdapter(item.getProducts(), null));
        binding.rvProduct.setNestedScrollingEnabled(false);
        binding.rvProduct.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(binding.getRoot().getContext() , androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));

        binding.tvTongTienHang.setText(AppUtils.customPrice(item.getPrices_order()));
        binding.tvTienGiamGiaSanPham.setText(AppUtils.customPrice(getDiscountAmount(item.getProducts())));
        binding.tvTongTienPhiVanChuyen.setText(AppUtils.customPrice(getShippingCost(item.getProducts())));
        binding.tvTongTienThanhToan.setText(AppUtils.customPrice(item.getPrices_order() - getDiscountAmount(item.getProducts()) + getShippingCost(item.getProducts())));

        if (isHideCheckbox) {
            binding.checkbox.setVisibility(View.GONE);
            if(item.getStatus().equals("4")) {
                binding.checkbox.setVisibility(View.GONE);
                binding.btnReview.setVisibility(View.VISIBLE);
                binding.btnReview.setOnClickListener(view -> {
                    for (ProductModel model : item.getProducts()) {
                        CartManager.addProduct(getProductModel(model.getProduct_id()));
                    }
                    Toast.makeText(binding.getRoot().getContext(), "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                });
            }else{
                binding.btnReview.setVisibility(View.GONE);
            }
        } else {
            binding.checkbox.setVisibility(View.VISIBLE);
            binding.btnReview.setVisibility(View.GONE);
            binding.checkbox.setChecked(item.isSelect());

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
    }


    private ProductModel getProductModel(String id) {
        for (ProductModel model : ListProducts) {
            if (model.getId().equals(id)) {
                return model;
            }
        }
        return null;
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
