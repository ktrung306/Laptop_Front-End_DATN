package com.hcm.sale_laptop.ui.adapter;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemPopularProductBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class PopularProductAdapter extends BaseAdapter<ProductModel, ItemPopularProductBinding> {

    public PopularProductAdapter(List<ProductModel> itemList, OnItemClick<ProductModel> listener) {
        super(itemList, listener);
    }

    @Override
    protected ItemPopularProductBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemPopularProductBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(ProductModel item, ItemPopularProductBinding binding, int position) {
        binding.txtProductName.setText(item.getTitle());

        final SpannableString price = AppUtils.customPrice(item.getPrice());
        // Set text vào TextView
        binding.txtPrice.setText(price);
        AppUtils.loadImageUrl(binding.imageView, item.getPicture());
    }
}
