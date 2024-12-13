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
import com.hcm.sale_laptop.data.local.prefs.KeyPref;
import com.hcm.sale_laptop.data.local.prefs.SharedPrefManager;
import com.hcm.sale_laptop.data.model.other.AddressModel;
import com.hcm.sale_laptop.databinding.FragmentAddNewAddressBinding;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.utils.AppUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddNewAddressFragment extends BaseFragment<BaseViewModel<?>, FragmentAddNewAddressBinding> {

    private OnNewAddressAdded onNewAddressAdded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddNewAddressBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi();
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
        setOnClickListener(mBinding.btnAddNewAddress, view -> {
            actionAddNewAddress();
        });
    }

    private void actionAddNewAddress() {
        final String name = mBinding.edtName.getText().toString();
        final String phoneNumber = mBinding.edtPhoneNumber.getText().toString();
        final String address = mBinding.edtAddress.getText().toString();

        if (name.isEmpty()) {
            showToast("Bạn chưa nhập Tên người nhận");
            return;
        }

        if (phoneNumber.isEmpty()) {
            showToast("Bạn chưa nhập SĐT người nhận");
            return;
        }

        if (address.isEmpty()) {
            showToast("Bạn chưa nhập Địa chỉ người nhận");
            return;
        }

        final AddressModel model = new AddressModel(name, phoneNumber, address, false);
        final Type type = new TypeToken<List<AddressModel>>() {
        }.getType();

        final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
        List<AddressModel> addressModels = shared.getListObject(KeyPref.KEY_ADDRESS, type);
        if (!AppUtils.checkListHasData(addressModels)) {
            addressModels = new ArrayList<>();
        }
        addressModels.add(model);
        shared.removeKey(KeyPref.KEY_ADDRESS);
        shared.saveListObject(KeyPref.KEY_ADDRESS, addressModels);
        showToast("Thêm Địa chỉ mới thành công");
        if (onNewAddressAdded != null) {
            onNewAddressAdded.onComplete(addressModels);
            onBack();
        }
    }

    @Override
    protected void setupData() {

    }

    private void hideOrShowBottomNavi() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(false);
    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    public void setOnNewAddressAdded(OnNewAddressAdded onNewAddressAdded) {
        this.onNewAddressAdded = onNewAddressAdded;
    }

    public interface OnNewAddressAdded {
        void onComplete(List<AddressModel> list);
    }
}
