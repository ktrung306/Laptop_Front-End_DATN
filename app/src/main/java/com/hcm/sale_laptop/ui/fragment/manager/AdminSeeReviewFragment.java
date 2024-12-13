package com.hcm.sale_laptop.ui.fragment.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.other.ReviewModel;
import com.hcm.sale_laptop.databinding.FragmentAdminSeeReviewBinding;
import com.hcm.sale_laptop.ui.adapter.AdminRateAdapter;
import com.hcm.sale_laptop.ui.viewmodel.ReviewViewModel;
import com.hcm.sale_laptop.ui.viewmodel.factory.ReviewViewModelFactory;


import java.util.ArrayList;
import java.util.List;

public class AdminSeeReviewFragment extends BaseFragment<ReviewViewModel, FragmentAdminSeeReviewBinding> {

    private AdminRateAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentAdminSeeReviewBinding.inflate(inflater, container, false);

        ReviewViewModelFactory factory = new ReviewViewModelFactory(RetrofitClient.getInstance().create(ApiService.class));
        mViewModel = new ViewModelProvider(this, factory).get(ReviewViewModel.class);

        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        adapter = new AdminRateAdapter(new ArrayList<>(), this::onClickReview);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setupAction() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::fetchReviews);
    }

    @Override
    protected void setupData() {
        fetchReviews();
        observeViewModel();
    }

    private void fetchReviews() {
        mViewModel.fetchReviews();
    }

    private void observeViewModel() {
        mViewModel.reviewList.observe(getViewLifecycleOwner(), this::updateReviews);
        mViewModel.errorMessage.observe(getViewLifecycleOwner(), this::showToast);
        mViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) showProgressBar();
            else hideProgressBar();
        });
    }

    private void updateReviews(List<ReviewModel> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            adapter.updateData(reviews);
        }
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    private void onClickReview(ReviewModel reviewModel) {
        showToast("Chọn sản phẩm: " + reviewModel.getProductTitle());
    }
}
