package com.hcm.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

public abstract class BaseAdapter<T, VB extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>> {

    private final OnItemClick<T> listener;

    // Danh sách dữ liệu mà Adapter quản lý
    private List<T> itemList;

    // Constructor để truyền danh sách dữ liệu
    public BaseAdapter(List<T> itemList, OnItemClick<T> listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    // Phương thức trừu tượng để inflate ViewBinding
    protected abstract VB createBinding(LayoutInflater inflater, ViewGroup parent);

    // Phương thức trừu tượng để bind dữ liệu vào ViewHolder
    protected abstract void bindData(T item, VB binding, int position);

    protected OnItemClick<T> getListener() {
        return listener;
    }

    // Tạo ViewHolder và inflate ViewBinding
    @NonNull
    @Override
    public BaseViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final VB binding = createBinding(inflater, parent);
        return new BaseViewHolder<>(binding);
    }

    // Gán dữ liệu cho ViewHolder
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VB> holder, int position) {
        if (position >= itemList.size()) return;
        final T item = itemList.get(position);
        bindData(item, holder.binding, position);
        // Thiết lập sự kiện onClick cho toàn bộ item
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    // Phương thức để cập nhật danh sách dữ liệu
    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<T> newItems) {
        this.itemList = newItems;
        notifyDataSetChanged();
    }

    public List<T> getItemList() {
        return this.itemList;
    }

    // Phương thức để thêm dữ liệu
    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<T> items) {
        this.itemList.addAll(items);
        notifyDataSetChanged();
    }

    // Phương thức để xóa dữ liệu
    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        if (this.itemList != null && position < this.itemList.size()) {
            this.itemList.remove(position);
            notifyDataSetChanged();
        }
    }

    // ViewHolder chung sử dụng ViewBinding
    public static class BaseViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {
        public final VB binding;

        public BaseViewHolder(VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

