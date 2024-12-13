package com.hcm.sale_laptop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hcm.base.BaseAdapter;
import com.hcm.base.OnItemClick;
import com.hcm.sale_laptop.data.model.other.AddressModel;
import com.hcm.sale_laptop.databinding.ItemAddressBinding;

import java.util.List;


public class AddressAdapter extends BaseAdapter<AddressModel, ItemAddressBinding> {
    List<AddressModel> itemList;

    public AddressAdapter(List<AddressModel> itemList, OnItemClick<AddressModel> listener) {
        super(itemList, listener);
        this.itemList = itemList;
    }

    public List<AddressModel> getItemList() {
        return itemList;
    }

    @Override
    protected ItemAddressBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemAddressBinding.inflate(inflater, parent, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void bindData(AddressModel item, ItemAddressBinding binding, int position) {
        binding.txtAddress.setText(item.getAddress());
        binding.checkbox.setChecked(item.isSelect());
        final String userNameAndPhone = item.getUserName() + " (" + item.getPhoneNumber() + ")";
        binding.txtUsernamePhone.setText(userNameAndPhone);
        binding.checkbox.setOnClickListener(view -> {
            for (AddressModel model : itemList) {
                model.setSelect(false);
            }

            item.setSelect(binding.checkbox.isChecked());

            binding.getRoot().post(this::notifyDataSetChanged);
            final OnItemClick<AddressModel> onItemClick = getListener();
            if (onItemClick != null) {
                onItemClick.onClick(item);
            }
        });
    }

}