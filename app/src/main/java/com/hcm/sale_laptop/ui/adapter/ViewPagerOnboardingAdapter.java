package com.hcm.sale_laptop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.OnboardingModel;
import com.hcm.sale_laptop.databinding.ItemPageOnboardingBinding;

import java.util.List;

public class ViewPagerOnboardingAdapter extends BaseAdapter<OnboardingModel, ItemPageOnboardingBinding> {

    public ViewPagerOnboardingAdapter(List<OnboardingModel> itemList, OnItemClick<OnboardingModel> listener) {
        super(itemList, listener);
    }

    @Override
    protected ItemPageOnboardingBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemPageOnboardingBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bindData(OnboardingModel item, ItemPageOnboardingBinding binding, int position) {
        binding.txtTitle.setText(item.getTitle());
        binding.ivCoverPhoto.setImageResource(item.getImage());
    }
}


