package com.hcm.sale_laptop.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPagerStateAdapter extends FragmentStateAdapter {

    final List<Fragment> arrayList;

    public ViewPagerStateAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
        super(fragment);
        this.arrayList = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}