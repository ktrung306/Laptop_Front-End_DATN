package com.hcm.sale_laptop.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.ItemReviewBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;


public class ReviewOrderAdapter extends BaseAdapter<ProductModel, ItemReviewBinding> {

    private final OnActionReview actionReview;

    public ReviewOrderAdapter(List<ProductModel> itemList, OnActionReview actionReview) {
        super(itemList, null);
        this.actionReview = actionReview;
    }

    @Override
    protected ItemReviewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemReviewBinding.inflate(inflater, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(ProductModel item, ItemReviewBinding binding, int position) {
        binding.tvNameProduct.setText(item.getTitle());
        binding.tvPrice.setText(AppUtils.customPrice(item.getPrice()));
        AppUtils.loadImageUrl(binding.imageView, item.getPicture());
        binding.tvQuantity.setText("" + item.getQuantity());

        binding.btnReOrder.setOnClickListener(view -> {
            if (actionReview != null) {
                actionReview.repurchase(item.getOrder_id());
            }
        });

        binding.btnReview.setOnClickListener(view -> {
            if (actionReview != null) {
                actionReview.orderReview(item);
            }
        });
    }

    public interface OnActionReview {
        void repurchase(String model);

        void orderReview(ProductModel model);
    }
}