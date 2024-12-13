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
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentAllDiscountedProductBinding;
import com.hcm.sale_laptop.ui.adapter.DiscountedProductAdapter;
import com.hcm.sale_laptop.ui.viewmodel.AllDiscountedProductViewModel;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.CartManager;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AllDiscountedProductFragment extends BaseFragment<AllDiscountedProductViewModel, FragmentAllDiscountedProductBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAllDiscountedProductBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi(false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        mBinding.rvDiscountedProduct.setLayoutManager(gridLayoutManager);
        final DiscountedProductAdapter adapter = new DiscountedProductAdapter(
                new ArrayList<>(), this::onClickDiscountedProduct);
        mBinding.rvDiscountedProduct.setAdapter(adapter);
    }

    private void onClickDiscountedProduct(ProductModel object) {
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

    private void showDialogWarning() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Lỗi");
        builder.setMessage("Giỏ hàng của bạn đang trống, hãy thêm ít nhất một sản phẩm vào giỏ hàng.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void setupData() {
        mViewModel = new AllDiscountedProductViewModel();
        mViewModel.fetch();
        mViewModel.errorMessage.observe(this, this::showToast);
        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });
        mViewModel.getProductSaleModels().observe(this, productSaleModels -> {
            final DiscountedProductAdapter adapter = (DiscountedProductAdapter) mBinding.rvDiscountedProduct.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(productSaleModels)) {
                adapter.setItems(productSaleModels);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideOrShowBottomNavi(true);
    }

    private void hideOrShowBottomNavi(boolean isShow) {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(isShow);
    }
}
