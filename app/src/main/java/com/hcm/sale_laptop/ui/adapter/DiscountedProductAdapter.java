package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemDiscountedProductBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class DiscountedProductAdapter extends BaseAdapter<ProductModel, ItemDiscountedProductBinding> {

    public DiscountedProductAdapter(List<ProductModel> itemList, OnItemClick<ProductModel> listener) {
        super(itemList, listener);
    }

    @Override
    protected ItemDiscountedProductBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemDiscountedProductBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(ProductModel item, ItemDiscountedProductBinding binding, int position) {
        binding.txtProductName.setText(item.getTitle());
        binding.txtOriginalPrice.setText(AppUtils.customPrice(item.getPrice()));
        AppUtils.loadImageUrl(binding.imageView, item.getPicture());
    }
}
