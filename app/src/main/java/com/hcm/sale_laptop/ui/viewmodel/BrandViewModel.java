package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.response.ProductResponse;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.data.model.other.ProductObject;
import com.hcm.sale_laptop.data.repository.BrandRepository;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BrandViewModel extends BaseViewModel<BrandRepository> {

    private final MutableLiveData<List<ProductModel>> productModels = new MutableLiveData<>();

    public BrandViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BrandRepository();
    }

    public BrandViewModel() {
        super();
        mRepository = new BrandRepository();
    }

    public LiveData<List<ProductModel>> getProductModels() {
        return productModels;
    }

    public void getProductsByBrand(String id) {
        final Disposable disposable = mRepository.getProductsByBrand(id)
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
