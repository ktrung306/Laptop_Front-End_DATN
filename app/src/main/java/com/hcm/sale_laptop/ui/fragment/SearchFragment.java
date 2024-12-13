package com.hcm.sale_laptop.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.reflect.TypeToken;
import com.hcm.base.BaseFragment;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.local.prefs.KeyPref;
import com.hcm.sale_laptop.data.local.prefs.SharedPrefManager;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.databinding.FragmentSearchBinding;
import com.hcm.sale_laptop.ui.adapter.PopularProductAdapter;
import com.hcm.sale_laptop.ui.adapter.SearchHistoryAdapter;
import com.hcm.sale_laptop.ui.viewmodel.MainActivityViewModel;
import com.hcm.sale_laptop.ui.viewmodel.SearchViewModel;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends BaseFragment<SearchViewModel, FragmentSearchBinding> {
    private final Handler handler = new Handler();
    private Runnable searchRunnable;
    private List<String> keywords;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSearchBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    @Override
    protected void setupUI() {
        hideOrShowBottomNavi();
        setupRVSearchHistory();
        setupRVSuggestProduct();
        setupEditTextSearch();
    }

    private void setupEditTextSearch() {

        mBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> {
                    // Called every time the text changes
                    final String query = charSequence.toString().trim();

                    // Perform API request if query is not empty
                    if (!query.isEmpty()) {
                        performSearchRequest(query);
                    }
                };

                handler.postDelayed(searchRunnable, 500); // Delay of 500 ms
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void performSearchRequest(String query) {
        mViewModel.searchProducts(query);
    }

    private void setupRVSuggestProduct() {
        final PopularProductAdapter adapter = new PopularProductAdapter(
                new ArrayList<>(), this::onClickSuggestProduct);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        mBinding.rvSuggestProduct.setLayoutManager(gridLayoutManager);
        mBinding.rvSuggestProduct.setAdapter(adapter);
    }

    private void setupRVSearchHistory() {
        final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
        final Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> keywords = shared.getListObject(KeyPref.KEY_SEARCH_HISTORY, type);

        if (!AppUtils.checkListHasData(keywords)) {
            mBinding.txtNotYetSearch.setVisibility(View.VISIBLE);
        } else {
            mBinding.txtNotYetSearch.setVisibility(View.GONE);
            this.keywords = keywords;
            Collections.reverse(keywords);
            mBinding.rvSearchHistory.setAdapter(new SearchHistoryAdapter(keywords, this::onItemClickSearchHistory));
        }
    }

    private void onClickSuggestProduct(ProductModel object) {
        final DetailProductFragment fragment = new DetailProductFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_PRODUCT_MODEL, object);
        fragment.setArguments(bundle);
        addFragment(fragment, R.id.fragment_container, true);
    }

    private void onItemClickSearchHistory(String item) {
        if (item != null && !item.isEmpty()) {
            mBinding.edtSearch.setText(item);
        }
    }

    @Override
    protected void setupAction() {
        setOnClickListener(mBinding.btnBackArrow, view -> onBack());
    }

    @Override
    protected void setupData() {
        mViewModel = new SearchViewModel();
        mViewModel.fetch();
        mViewModel.errorMessage.observe(this, this::showToast);
        mViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        mViewModel.getProductModels().observe(this, productModels -> {
            final PopularProductAdapter adapter = (PopularProductAdapter) mBinding.rvSuggestProduct.getAdapter();
            if (adapter != null && AppUtils.checkListHasData(productModels)) {
                Collections.reverse(productModels);
                if (productModels.size() > 6) {
                    adapter.setItems(productModels.subList(0, 6));
                } else {
                    adapter.setItems(productModels);
                }
            }
        });

        mViewModel.getSearchProducts().observe(this, productModels -> {
            final PopularProductAdapter adapter = (PopularProductAdapter) mBinding.rvSuggestProduct.getAdapter();
            if (adapter != null) {
                adapter.setItems(productModels);
            }
            if (mBinding.edtSearch.getText() == null) {
                return;
            }

            if (AppUtils.checkListHasData(productModels)) {
                if (this.keywords == null) {
                    this.keywords = new ArrayList<>();
                    this.mBinding.txtNotYetSearch.setVisibility(View.GONE);
                }
                final String key = mBinding.edtSearch.getText().toString().trim();
                if (!this.keywords.contains(key)) {
                    this.keywords.add(key);
                }
            }
        });

        mViewModel.showUISearch().observe(this, this::setHideOrShowUI);
    }

    private List<View> getListUI() {
        return new ArrayList<View>() {{
            add(mBinding.txtSuggestSearch);
            add(mBinding.rvSearchHistory);
            add(mBinding.line);
        }};
    }

    private void setHideOrShowUI(boolean isShow) {
        for (View view : getListUI()) {
            if (isShow) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return mBinding.getRoot().getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!AppUtils.checkListHasData(keywords)) {
            return;
        }

        final SharedPrefManager shared = SharedPrefManager.getInstance(requireContext());
        shared.removeKey(KeyPref.KEY_SEARCH_HISTORY);

        if (keywords.size() > 10) {
            keywords = keywords.subList(keywords.size() - 10, keywords.size());
        }

        shared.saveListObject(KeyPref.KEY_SEARCH_HISTORY, this.keywords);
    }

    private void hideOrShowBottomNavi() {
        final MainActivityViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainViewModel.setBottomNavVisibility(false);
    }
}
