package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.reflect.TypeToken;
import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.local.prefs.KeyPref;
import com.hcm.sale_laptop.data.local.prefs.SharedPrefManager;
import com.hcm.sale_laptop.data.model.other.AddressModel;
import com.hcm.sale_laptop.databinding.FragmentShippingAddressBinding;
import com.hcm.sale_laptop.ui.adapter.AddressAdapter;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.utils.AppUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShippingAddressFragment extends BaseFragment<BaseViewModel<?>, FragmentShippingAddressBinding> implements AddNewAddressFragment.OnNewAddressAdded {

    private AddressModel addressModel = null;
    private OnSelectedAddress selectedAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentShippingAddressBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi();
        setupRVAddress();
    }

    private void hideOrShowBottomNavi() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(false);
    }

    private void setupRVAddress() {
        final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
        final Type type = new TypeToken<List<AddressModel>>() {
        }.getType();
        final List<AddressModel> list = shared.getListObject(KeyPref.KEY_ADDRESS, type);
        if (!AppUtils.checkListHasData(list)) {
            mBinding.rvAddress.setAdapter(new AddressAdapter(new ArrayList<>(), this::onItemClick));
            return;
        }
        mBinding.rvAddress.setAdapter(new AddressAdapter(list, this::onItemClick));

        mBinding.btnDelete.setOnClickListener(v -> {
            final AddressAdapter adapter = (AddressAdapter) mBinding.rvAddress.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(adapter.getItemList())) {
                final List<AddressModel> listAddress = adapter.getItemList();
                listAddress.remove(addressModel);
                adapter.setItems(listAddress);
                shared.removeKey(KeyPref.KEY_ADDRESS);
                shared.saveListObject(KeyPref.KEY_ADDRESS, listAddress);
            }
        });
    }

    private void onItemClick(AddressModel model) {
        this.addressModel = model.isSelect() ? model : null;
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
        setOnClickListener(mBinding.btnConfirm, view -> {
            final AddressAdapter adapter = (AddressAdapter) mBinding.rvAddress.getAdapter();
            if (adapter != null && Objects.requireNonNull(adapter).getItemCount() == 0) {
                showToast("Bạn chưa có địa chỉ nào trong danh sách");
                return;
            }
            if (addressModel == null) {
                showToast("Bạn chưa chọn địa chỉ nào");
                return;
            }
            if (adapter != null && AppUtils.checkListHasData(adapter.getItemList())) {
                final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
                shared.removeKey(KeyPref.KEY_ADDRESS);
                shared.saveListObject(KeyPref.KEY_ADDRESS, adapter.getItemList());
                if (selectedAddress != null) {
                    selectedAddress.onSelectedAddress(this.addressModel);
                    onBack();
                }
            }
        });

        setOnClickListener(mBinding.fab, view -> {
            AddNewAddressFragment fragment = new AddNewAddressFragment();
            fragment.setOnNewAddressAdded(this);
            addFragment(fragment, R.id.fragment_container, true);
        });
    }

    public void setSelectedAddress(OnSelectedAddress address) {
        this.selectedAddress = address;
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onComplete(List<AddressModel> list) {
        final AddressAdapter adapter = (AddressAdapter) mBinding.rvAddress.getAdapter();
        if (adapter != null && AppUtils.checkListHasData(list)) {
            adapter.setItems(list);
        }
    }

    public interface OnSelectedAddress {
        void onSelectedAddress(AddressModel model);
    }
}
