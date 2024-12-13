package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.base.BaseFragment;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.model.network.request.CancelOrderRequest;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentOrderBinding;
import com.hcm.sale_laptop.ui.adapter.ReviewOrderAdapter;
import com.hcm.sale_laptop.ui.adapter.ViewPagerStateAdapter;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.ui.viewmodel.WaitingPickupViewModel;
import com.hcm.sale_laptop.utils.CartManager;
import com.hcm.sale_laptop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment<BaseViewModel<?>, FragmentOrderBinding>
        implements ReviewOrderAdapter.OnActionReview {

    private boolean isFromOrderReview = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentOrderBinding.inflate(inflater, container, false);

        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(true);

        final OrderReviewFragment orderReviewFragment = new OrderReviewFragment();
        orderReviewFragment.setOnActionReview(this);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WaitConfirmFragment());
        fragments.add(new WaitPickupFragment());
        fragments.add(new WaitDeliveryFragment());
        fragments.add(orderReviewFragment);

        final ViewPagerStateAdapter viewPagerStateAdapter = new ViewPagerStateAdapter(this, fragments);
        mBinding.viewPager.setAdapter(viewPagerStateAdapter);
        mBinding.viewPager.setUserInputEnabled(false);

        if (isFromOrderReview) {
            final int redColor = getResources().getColor(com.hcm.base.R.color.red, null);
            mBinding.tvTab4.setTextColor(redColor);

            final int blackColor = getResources().getColor(R.color.black, null);
            mBinding.tvTab1.setTextColor(blackColor);

            this.isFromOrderReview = false;
        }
    }

    @Override
    protected void setupAction() {
        mBinding.tab1.setOnClickListener(view -> updateViewPager(0));
        mBinding.tab2.setOnClickListener(view -> updateViewPager(1));
        mBinding.tab3.setOnClickListener(view -> updateViewPager(2));
        mBinding.tab4.setOnClickListener(view -> updateViewPager(3));
    }

    private void updateViewPager(int tab) {
        final int blackColor = getResources().getColor(R.color.black, null);
        final int redColor = getResources().getColor(com.hcm.base.R.color.red, null);

        mBinding.tvTab1.setTextColor(blackColor);
        mBinding.tvTab2.setTextColor(blackColor);
        mBinding.tvTab3.setTextColor(blackColor);
        mBinding.tvTab4.setTextColor(blackColor);

        switch (tab) {
            case 0:
                mBinding.tvTab1.setTextColor(redColor);
                break;
            case 1:
                mBinding.tvTab2.setTextColor(redColor);
                break;
            case 2:
                mBinding.tvTab3.setTextColor(redColor);
                break;
            case 3:
                mBinding.tvTab4.setTextColor(redColor);
                break;
        }

        mBinding.viewPager.setCurrentItem(tab);
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void repurchase(String model) {
        isFromOrderReview = true;
//        CartManager.addProduct(model);
//        addFragment(new ShoppingCartFragment(), R.id.fragment_container, true);
        final CancelOrderRequest request = new CancelOrderRequest( model, "","6");
        WaitingPickupViewModel viewModel = new WaitingPickupViewModel();
        viewModel.cancelOrder(request);
        setup();
    }

    @Override
    public void orderReview(ProductModel model) {
        isFromOrderReview = true;
        final SubmitReviewFragment fragment = new SubmitReviewFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_PRODUCT_MODEL, model);
        fragment.setArguments(bundle);
        addFragment(fragment, R.id.fragment_container, true);
    }
}