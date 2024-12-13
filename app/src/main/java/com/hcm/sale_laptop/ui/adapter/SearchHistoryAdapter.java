package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.databinding.ItemSearchHistoryBinding;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter<String, ItemSearchHistoryBinding> {

    public SearchHistoryAdapter(List<String> itemList, OnItemClick<String> listener) {
        super(itemList, listener);
    }

    @Override
    protected ItemSearchHistoryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemSearchHistoryBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(String item, ItemSearchHistoryBinding binding, int position) {
        binding.itemTitle.setText(item);
    }
}


