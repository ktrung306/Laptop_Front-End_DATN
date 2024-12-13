package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentOrderReviewBinding;
import com.hcm.sale_laptop.ui.adapter.ReviewOrderAdapter;
import com.hcm.sale_laptop.ui.viewmodel.OrderReviewViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderReviewFragment extends BaseFragment<OrderReviewViewModel, FragmentOrderReviewBinding> implements ReviewOrderAdapter.OnActionReview {
    private ReviewOrderAdapter.OnActionReview onActionReview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentOrderReviewBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    public void setOnActionReview(ReviewOrderAdapter.OnActionReview onActionReview) {
        this.onActionReview = onActionReview;
    }

    @Override
    protected void setupUI() {
        final ReviewOrderAdapter adapter = new ReviewOrderAdapter(
                new ArrayList<>(), this);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setupAction() {

        mViewModel = new OrderReviewViewModel(requireContext());

        mViewModel.getReview(Constants.getUserModel().getId());

        mViewModel.errorMessage.observe(this, this::showToast);

        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getDataReview().observe(this, orderStateModels -> {
            final ReviewOrderAdapter adapter = (ReviewOrderAdapter) mBinding.recyclerView.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(orderStateModels)) {
                final List<ProductModel> list = new ArrayList<>();
                for (OrderStateModel model : orderStateModels) {
                    if (!model.getStatus().equals("3")) {
                        continue;
                    }
                   for (ProductModel productModel : model.getProducts()) {
                       productModel.setOrder_id(model.getId());
                       list.add(productModel);
                   }
                }
                adapter.setItems(list);
            }
        });
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void repurchase(String model) {
        if (onActionReview != null) {
            onActionReview.repurchase(model);
        }
    }

    @Override
    public void orderReview(ProductModel model) {
        if (onActionReview != null) {
            onActionReview.orderReview(model);
        }
    }
}
