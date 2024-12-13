package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentProductDetailBinding;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.utils.AppLogger;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.CartManager;
import com.hcm.sale_laptop.utils.Constants;

public class DetailProductFragment extends BaseFragment<BaseViewModel<?>, FragmentProductDetailBinding> {

    private ProductModel productModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentProductDetailBinding.inflate(inflater, container, false);

        final Bundle bundle = getArguments();
        if (bundle == null) return mBinding.getRoot();

        final ProductModel productModel = bundle.getParcelable(Constants.KEY_PRODUCT_MODEL);
        if (productModel == null) return mBinding.getRoot();

        this.productModel = productModel;

        setup();

        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi();

        mBinding.txtProductName.setText(productModel.getTitle());
        mBinding.btnAddCart.setActivated(true);
        mBinding.txtDescription.setText(productModel.getDescription());
        final SpannableString price = AppUtils.customPrice(productModel.getPrice());
        mBinding.txtPrice.setText(price);

        AppUtils.loadImageUrl(mBinding.imageView, productModel.getPicture());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());

        setOnClickListener(mBinding.btnAddCart, view -> {
            CartManager.addProduct(productModel.clone());
            showToast("Đã thêm sản phẩm " + productModel.getTitle() + " vào giỏ hàng");
        });

        setOnClickListener(mBinding.btnBuyNow, view -> {
            AppLogger.d("productModel : ", productModel.getOrderNumber() + "- " + productModel.getTotalAmount());
            CartManager.addProduct(productModel.clone());
            addFragment(new ShoppingCartFragment(), R.id.fragment_container, true);
        });
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    private void hideOrShowBottomNavi() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(false);
    }
}
