package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.model.other.BrandModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentBrandBinding;
import com.hcm.sale_laptop.ui.adapter.PopularProductAdapter;
import com.hcm.sale_laptop.ui.viewmodel.BrandViewModel;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.CartManager;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends BaseFragment<BrandViewModel, FragmentBrandBinding> {

    private BrandModel brandModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentBrandBinding.inflate(inflater, container, false);
        final Bundle bundle = getArguments();
        if (bundle == null) return mBinding.getRoot();

        final BrandModel model = bundle.getParcelable(Constants.KEY_BRAND_MODEL);
        if (model == null) return mBinding.getRoot();

        this.brandModel = model;

        setup();

        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi();
        mBinding.txtHeader.setText(brandModel.getName());
        final PopularProductAdapter adapter = new PopularProductAdapter(
                new ArrayList<>(), this::onClickProduct);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        mBinding.rvProduct.setLayoutManager(gridLayoutManager);
        mBinding.rvProduct.setAdapter(adapter);
    }

    private void onClickProduct(ProductModel object) {
        final DetailProductFragment fragment = new DetailProductFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_PRODUCT_MODEL, object);
        fragment.setArguments(bundle);
        addFragment(fragment, R.id.fragment_container, true);
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
        setOnClickListener(mBinding.btnShoppingCart, view -> {
            final List<ProductModel> list = CartManager.getOrderList();
            if (AppUtils.checkListHasData(list)) {
                addFragment(new ShoppingCartFragment(), R.id.fragment_container, true);
                return;
            }
            showDialogWarning();
        });
    }

    @Override
    protected void setupData() {
        mViewModel = new BrandViewModel();
        mViewModel.getProductsByBrand(brandModel.getId());
        mViewModel.errorMessage.observe(this, this::showToast);
        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getProductModels().observe(this, productModels -> {
            final PopularProductAdapter adapter = (PopularProductAdapter) mBinding.rvProduct.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(productModels)) {
                adapter.setItems(productModels);
            }
        });
    }

    private void showDialogWarning() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Lỗi");
        builder.setMessage("Giỏ hàng của bạn đang trống, hãy thêm ít nhất một sản phẩm vào giỏ hàng.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void hideOrShowBottomNavi() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(false);
    }
}
