package com.hcm.sale_laptop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemProductBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class ItemProductAdapter extends BaseAdapter<ProductModel, ItemProductBinding> {
    public ItemProductAdapter(List<ProductModel> itemList, OnItemClick<ProductModel> listener) {
        super(itemList, listener);
    }

    @Override
    protected ItemProductBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemProductBinding.inflate(inflater, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(ProductModel item, ItemProductBinding binding, int position) {
        binding.tvNameProduct.setText(item.getTitle());
        binding.tvPrice.setText(AppUtils.customPrice(item.getPrice()));
        binding.tvQuantity.setText("" + item.getQuantity());
        AppUtils.loadImageUrl(binding.imageView, item.getPicture());
    }
}
