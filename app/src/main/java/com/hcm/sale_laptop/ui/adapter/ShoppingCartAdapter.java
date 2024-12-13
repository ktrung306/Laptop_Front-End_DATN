package com.hcm.sale_laptop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemCartBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter<ProductModel, ItemCartBinding> {

    private final List<ProductModel> productModels;
    private final boolean isEdit;
    private OnValueChanged onValueChanged;

    private final List<String> selectedProductIds;

    public ShoppingCartAdapter(List<ProductModel> itemList, OnItemClick<ProductModel> listener, boolean isEdit, List<String> selectedProductIds) {
        super(itemList, listener);
        this.productModels = itemList;
        this.isEdit = isEdit;
        this.selectedProductIds = selectedProductIds;
    }


    public void setOnValueChanged(OnValueChanged onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    @Override
    protected ItemCartBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemCartBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(ProductModel model, ItemCartBinding binding, int position) {
        binding.checkbox.setChecked(selectedProductIds.contains(model.getId()));
        if (!isEdit) {
            binding.checkbox.setVisibility(View.GONE);
        }
        AppUtils.loadImageUrl(binding.imageView, model.getPicture());

        if (model.getTotalAmount() == 0) {
            model.setTotalAmount(model.getPrice());
        }
        binding.txtProductName.setText(model.getTitle());
        binding.txtPrice.setText(AppUtils.customPrice(model.getTotalAmount()));

        final String quantityString;
        if (model.getOrderNumber() < 10) {
            quantityString = "0" + model.getOrderNumber();
        } else {
            quantityString = String.valueOf(model.getOrderNumber());
        }
        binding.txtValueQuantity.setText(quantityString);

        if (isEdit) {
            handlerAction(binding, position, model);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void handlerAction(ItemCartBinding binding, int position, ProductModel model) {
        binding.btnMinus.setOnClickListener(view -> {
            if (model.getOrderNumber() <= 1) return;
            model.setOrderNumber(model.getOrderNumber() - 1);
            reloadPrice(model, position);
        });

        binding.btnPlus.setOnClickListener(view -> {
            model.setOrderNumber(model.getOrderNumber() + 1);
            reloadPrice(model, position);
        });

        binding.checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (onValueChanged != null) {
                onValueChanged.onCheckBoxChanged(isChecked, model.getId());
            }
        });
    }

    private void reloadPrice(ProductModel model, int position) {
        float newPrice = model.getPrice() * model.getOrderNumber();
        model.setTotalAmount(newPrice);
        double totalAmount = 0;

        for (ProductModel element : productModels) {
            totalAmount += element.getTotalAmount();
        }
        notifyItemChanged(position);

        if (onValueChanged != null) {
            onValueChanged.onTotalAmountChanged(totalAmount);
        }
    }

    public interface OnValueChanged {
        void onTotalAmountChanged(double totalAmount);

        void onCheckBoxChanged(boolean isCheck, String productId);
    }
}

