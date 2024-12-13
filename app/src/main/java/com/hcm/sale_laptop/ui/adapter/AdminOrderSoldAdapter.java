package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcm.sale_laptop.data.model.network.response.SoldOrderResponse;
import com.hcm.sale_laptop.databinding.ItemOrderSoldBinding;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

public class AdminOrderSoldAdapter extends RecyclerView.Adapter<AdminOrderSoldAdapter.ViewHolder> {

    private final List<SoldOrderResponse> itemList;

    public AdminOrderSoldAdapter(List<SoldOrderResponse> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderSoldBinding binding = ItemOrderSoldBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoldOrderResponse item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public void updateData(List<SoldOrderResponse> newItems) {
        itemList.clear();
        itemList.addAll(newItems);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemOrderSoldBinding binding;

        public ViewHolder(@NonNull ItemOrderSoldBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SoldOrderResponse item) {
            // Set order data
            binding.tvMaDonHang.setText(item.getOrderId());
            binding.tvNameProduct.setText(item.getProductTitle());
            binding.tvPrice.setText(AppUtils.formatCurrency((long) item.getProductPrice()));
            binding.tvQuantity.setText(String.valueOf(item.getQuantitySold()));
            binding.tvNgay.setText(item.getOrderDate());

            // Load product image
            Glide.with(binding.getRoot().getContext())
                    .load(item.getProductPicture())
                    .into(binding.orderImage);
        }
    }
}
