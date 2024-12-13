package com.hcm.sale_laptop.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.google.gson.reflect.TypeToken;
import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.local.prefs.KeyPref;
import com.hcm.sale_laptop.data.local.prefs.SharedPrefManager;
import com.hcm.sale_laptop.data.model.network.request.OrderRequest;
import com.hcm.sale_laptop.data.model.other.AddressModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.data.model.other.ProductOrderModel;
import com.hcm.sale_laptop.databinding.FragmentPaymentsBinding;
import com.hcm.sale_laptop.ui.adapter.ShoppingCartAdapter;
import com.hcm.sale_laptop.ui.viewmodel.PaymentsViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.CartManager;
import com.hcm.sale_laptop.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaymentsFragment extends BaseFragment<PaymentsViewModel, FragmentPaymentsBinding> implements ShippingAddressFragment.OnSelectedAddress {

    private double totalAmount;
    private List<ProductModel> selectedProducts = new ArrayList<>();
    private List<String> listProductId = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPaymentsBinding.inflate(inflater, container, false);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            totalAmount = bundle.getDouble(Constants.KEY_TOTAL_AMOUNT, 0);
            Log.d("totalAmount", "onCreateView: " + totalAmount);
            selectedProducts = bundle.getParcelableArrayList(Constants.KEY_SELECTED_PRODUCTS);
            for (ProductModel model : selectedProducts) {
                listProductId.add(model.getId());
            }
        }
        setup();
        return mBinding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void setupUI() {
        final ShoppingCartAdapter adapter = new ShoppingCartAdapter(selectedProducts, null, false, listProductId);
        mBinding.rvProductCart.setAdapter(adapter);

        mBinding.txtTotalOrderAmount.setText(AppUtils.customPrice(totalAmount));
        mBinding.txtTotalShippingCost.setText(AppUtils.customPrice(getShippingCost()));
        mBinding.txtDiscountAmount.setText(AppUtils.customPriceReduced(getDiscountAmount()));

        final double totalPayment = totalAmount + getShippingCost() - getDiscountAmount();
        mBinding.txtTotalPayment.setText(AppUtils.customPrice(totalPayment));
        final Type type = new TypeToken<List<AddressModel>>() {
        }.getType();

        final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
        List<AddressModel> addressModels = shared.getListObject(KeyPref.KEY_ADDRESS, type);
        if (AppUtils.checkListHasData(addressModels)) {
            for (AddressModel model : addressModels) {
                if (model.isSelect()) {
                    setTextAddress(model);
                    break;
                }
            }
        }
    }

    private double getDiscountAmount() {
        return getOrderNumber() * 150000;
    }

    private double getShippingCost() {
        return getOrderNumber() * 10000;
    }

    private int getOrderNumber() {
        int number = 0;
        for (ProductModel model : selectedProducts) {
            number += (int) model.getOrderNumber();
        }
        return number;
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
        setOnClickListener(mBinding.btnPayment, view -> {
            final String address = mBinding.txtAddress.getText().toString();
            if (address.isEmpty()) {
                showToast("Bạn chưa nhập địa chỉ giao hàng");
            } else {
                showDialogWarning();
            }
        });

        setOnClickListener(mBinding.btnEditLocation, view -> {
            final ShippingAddressFragment fragment = new ShippingAddressFragment();
            fragment.setSelectedAddress(this);
            addFragment(fragment, R.id.fragment_container, true);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showDialogWarning() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn muốn giao hàng đến người nhận: " + mBinding.txtAddress.getText().toString());

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            final double totalPayment = totalAmount + getShippingCost() - getDiscountAmount();
            final List<ProductOrderModel> list = new ArrayList<>();
            final OrderRequest request = new OrderRequest();

            for (ProductModel model : selectedProducts) {
                ProductOrderModel product = new ProductOrderModel(model.getId(), (int) model.getOrderNumber());
                list.add(product);
            }
            request.setAddress(mBinding.txtAddress.getText().toString().trim());
            request.setUser_id(Constants.getUserModel().getId());
            request.setTotal_product_amount(totalAmount);
            request.setTotal_shipping_cost(getShippingCost());
            request.setProduct_discount_amount(getDiscountAmount());
            request.setTotal_payment(totalPayment);
            request.setProducts(list);

            mViewModel.order(request);

            dialog.dismiss();
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handlerOrderSuccess() {
        for (String id : listProductId) {
            CartManager.removeProduct(id);
        }
        final FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void setupData() {
        mViewModel = new PaymentsViewModel();
        mViewModel.errorMessage.observe(this, this::showToast);
        mViewModel.successMessage.observe(this, this::showToast);
        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getIsOrderSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                handlerOrderSuccess();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onSelectedAddress(AddressModel model) {
        setTextAddress(model);
    }

    private void setTextAddress(AddressModel model) {
        final String value = model.getUserName() + " (" +
                model.getPhoneNumber() +
                ") \n" +
                model.getAddress();
        mBinding.txtAddress.setText(value);
    }
}
