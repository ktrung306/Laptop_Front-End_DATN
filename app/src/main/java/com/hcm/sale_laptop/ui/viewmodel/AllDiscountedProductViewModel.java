package com.hcm.sale_laptop.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.response.ProductSaleResponse;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.data.model.other.ProductSaleObject;
import com.hcm.sale_laptop.data.repository.AllDiscountedProductRepository;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllDiscountedProductViewModel extends BaseViewModel<AllDiscountedProductRepository> {

    private final MutableLiveData<List<ProductModel>> productSaleModels = new MutableLiveData<>();

    public AllDiscountedProductViewModel() {
        super();
        mRepository = new AllDiscountedProductRepository();
    }

    public void fetch() {
        setLoading(true);
        getDataProductSaleModels();
    }

    public LiveData<List<ProductModel>> getProductSaleModels() {
        return productSaleModels;
    }

    private void getDataProductSaleModels() {
        final Disposable disposable = mRepository.getDataProductSales()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerProductSaleResponse, throwable -> setErrorMessage(throwable.getMessage())
                );
        addDisposable(disposable);
    }

    private void handlerProductSaleResponse(ProductSaleResponse response) {
        setLoading(false);
        final ProductSaleObject object = response.getData();
        if (!response.isSuccess() || object == null) {
            setErrorMessage("Lỗi load danh sách sản phẩm");
        }

        final List<ProductModel> productSaleModels = object != null ? object.getProductModels() : null;
        if (!AppUtils.checkListHasData(productSaleModels)) {
            setErrorMessage("Lỗi load danh sách sản phẩm");
            return;
        }
        this.productSaleModels.setValue(productSaleModels);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
