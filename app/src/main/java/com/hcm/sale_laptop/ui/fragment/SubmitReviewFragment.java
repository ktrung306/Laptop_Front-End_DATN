package com.hcm.sale_laptop.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.data.model.network.request.ReviewRequest;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentSubmitReviewBinding;
import com.hcm.sale_laptop.ui.viewmodel.OrderReviewViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

public class SubmitReviewFragment extends BaseFragment<OrderReviewViewModel, FragmentSubmitReviewBinding> {
    private ProductModel productModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSubmitReviewBinding.inflate(inflater, container, false);

        // Nhận dữ liệu từ Bundle
        final Bundle bundle = getArguments();
        if (bundle == null || !bundle.containsKey(Constants.KEY_PRODUCT_MODEL)) {
            showToast("Không tìm thấy sản phẩm.");
            requireActivity().onBackPressed(); // Quay lại trang trước
            return mBinding.getRoot();
        }

        productModel = bundle.getParcelable(Constants.KEY_PRODUCT_MODEL);
        if (productModel == null) {
            showToast("Dữ liệu sản phẩm không hợp lệ.");
            requireActivity().onBackPressed();
            return mBinding.getRoot();
        }

        setup(); // Cài đặt UI
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        Log.d("SubmitReviewFragment", "setupUI: " + productModel.getId());
        AppUtils.loadImageUrl(mBinding.imageView, productModel.getPicture());
        mBinding.tvNameProduct.setText(productModel.getTitle());
        mBinding.tvPrice.setText(AppUtils.customPrice(productModel.getPrice()));
        mBinding.tvQuantity.setText(String.valueOf(productModel.getQuantity()));
    }

    @Override
    protected void setupAction() {
        mViewModel = new OrderReviewViewModel(requireContext());

        // Lắng nghe thay đổi
        mViewModel.errorMessage.observe(getViewLifecycleOwner(), this::showToast);
        mViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) showProgressBar();
            else hideProgressBar();
        });

        // Xử lý sự kiện bấm nút "Submit"
        mBinding.btnSubmit.setOnClickListener(view -> {
            final String content = mBinding.etReason.getText().toString().trim();
            final float rating = mBinding.ratingBar.getRating();

            if (content.isEmpty()) {
                showToast("Vui lòng nhập nội dung đánh giá.");
                return;
            }
            if (rating <= 0 || rating > 5) {
                showToast("Vui lòng chọn số sao từ 1 đến 5.");
                return;
            }

            final ReviewRequest request = new ReviewRequest(
                    Constants.getUserModel().getId(),
                    productModel.getProduct_id(),
                    rating,
                    content
            );
            mViewModel.submitReview(request);
        });

        mBinding.btnBackArrow.setOnClickListener(view -> requireActivity().onBackPressed());
    }

    @Override
    protected void setupData() {

    }
}