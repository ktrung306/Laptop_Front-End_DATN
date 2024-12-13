package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.response.ProductResponse;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.data.model.other.ProductObject;
import com.hcm.sale_laptop.data.repository.SearchRepository;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel<SearchRepository> {

    private final MutableLiveData<List<ProductModel>> productModels = new MutableLiveData<>();

    private final MutableLiveData<List<ProductModel>> searchProducts = new MutableLiveData<>();

    private final MutableLiveData<Boolean> showUISearch = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SearchRepository();
    }

    public SearchViewModel() {
        super();
        mRepository = new SearchRepository();
    }

    public void fetch() {
        setLoading(true);
        getDataProducts();
    }

    public LiveData<List<ProductModel>> getProductModels() {
        return productModels;
    }

    public LiveData<List<ProductModel>> getSearchProducts() {
        return searchProducts;
    }

    public LiveData<Boolean> showUISearch() {
        return showUISearch;
    }

    public void searchProducts(String keyWord) {
        final Disposable disposable = mRepository.searchProducts(keyWord)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchProductResponse, throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void searchProductResponse(ProductResponse response) {
        setLoading(false);
        final ProductObject object = response.getData();
        if (object == null) {
            setErrorMessage("Lỗi khi kết nối với máy chủ");
            this.searchProducts.setValue(new ArrayList<>());
            this.showUISearch.setValue(true);
            return;
        }

        final List<ProductModel> productModels = object.getProductModels();
        if (!AppUtils.checkListHasData(productModels) || !response.isSuccess()) {
            setErrorMessage("Không tìm thấy sản phẩm phù hợp");
            this.searchProducts.setValue(new ArrayList<>());
            this.showUISearch.setValue(true);
            return;
        }
        this.showUISearch.setValue(false);
        this.searchProducts.setValue(productModels);
    }

    private void getDataProducts() {
        final Disposable disposable = mRepository.getDataProducts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerProductResponse, throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void handlerProductResponse(ProductResponse response) {
        setLoading(false);
        final ProductObject object = response.getData();
        if (!response.isSuccess() || object == null) {
            setErrorMessage("Lỗi load danh sách sản phẩm");
        }

        final List<ProductModel> productModels = object != null ? object.getProductModels() : null;
        if (!AppUtils.checkListHasData(productModels)) {
            setErrorMessage("Lỗi load danh sách sản phẩm");
            return;
        }

        this.productModels.setValue(productModels);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
