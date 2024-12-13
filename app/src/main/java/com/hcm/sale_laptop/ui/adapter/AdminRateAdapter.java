package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcm.sale_laptop.data.model.other.ReviewModel;
import com.hcm.sale_laptop.databinding.ItemEvaluateBinding;

import java.util.List;

public class AdminRateAdapter extends RecyclerView.Adapter<AdminRateAdapter.ViewHolder> {

    private List<ReviewModel> reviewList;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ReviewModel reviewModel);
    }

    public AdminRateAdapter(List<ReviewModel> reviewList, OnItemClickListener onItemClickListener) {
        this.reviewList = reviewList;
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(List<ReviewModel> reviews) {
        this.reviewList = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEvaluateBinding binding = ItemEvaluateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewModel review = reviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemEvaluateBinding binding;

        public ViewHolder(ItemEvaluateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ReviewModel review) {
            binding.tvMaDonHang.setText(review.getOrderId());
            binding.tvNgay.setText(review.getOrderDate());
            binding.tvNameProduct.setText(review.getProductTitle());
            binding.tvNameClient.setText(review.getUserName());
            binding.ratingBar.setRating(review.getRating());
            binding.ratingBar.setIsIndicator(true);
            binding.tvContent.setText(review.getComment());

            binding.getRoot().setOnClickListener(view -> onItemClickListener.onItemClick(review));
        }
    }
}
