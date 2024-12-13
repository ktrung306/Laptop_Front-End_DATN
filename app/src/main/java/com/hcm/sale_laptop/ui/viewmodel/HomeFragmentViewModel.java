package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.response.BannerResponse;
import com.hcm.sale_laptop.data.model.network.response.BrandResponse;
import com.hcm.sale_laptop.data.model.network.response.ProductResponse;
import com.hcm.sale_laptop.data.model.network.response.ProductSaleResponse;
import com.hcm.sale_laptop.data.model.other.BannerModel;
import com.hcm.sale_laptop.data.model.other.BannerObject;
import com.hcm.sale_laptop.data.model.other.BrandModel;
import com.hcm.sale_laptop.data.model.other.CategoryModel;
import com.hcm.sale_laptop.data.model.other.ProductModel;
import com.hcm.sale_laptop.data.model.other.ProductObject;
import com.hcm.sale_laptop.data.model.other.ProductSaleObject;
import com.hcm.sale_laptop.data.repository.HomeRepository;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragmentViewModel extends BaseViewModel<HomeRepository> {

    private final MutableLiveData<List<BrandModel>> brandModels = new MutableLiveData<>();
    private final MutableLiveData<List<ProductModel>> productModels = new MutableLiveData<>();
    private final MutableLiveData<List<BannerModel>> bannerModels = new MutableLiveData<>();
    private final MutableLiveData<List<ProductModel>> productSaleModels = new MutableLiveData<>();
    public static List<ProductModel> ListProducts = new ArrayList<>();
    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HomeRepository();
    }

    public void fetch() {
        setLoading(true);
        getDataBanners();
        getDataBrand();
        getDataProducts();
        getDataProductSaleModels();
    }

    public LiveData<List<BrandModel>> getBrandModels() {
        return brandModels;
    }

    public LiveData<List<ProductModel>> getProductSaleModels() {
        return productSaleModels;
    }

    public LiveData<List<ProductModel>> getProductModels() {
        return productModels;
    }

    public LiveData<List<BannerModel>> getBannerModels() {
        return bannerModels;
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

    private void getDataBanners() {
        final Disposable disposable = mRepository.getDataBanners()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerBannerResponse, throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void getDataProducts() {
        final Disposable disposable = mRepository.getDataProducts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerProductResponse,
                        throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void getDataBrand() {
        final Disposable disposable = mRepository.getDataBrand()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerBrandResponse,
                        throwable -> setErrorMessage(throwable.getMessage()));
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
        ListProducts.addAll(productModels);
        this.productModels.setValue(productModels);
    }

    private void handlerBrandResponse(BrandResponse response) {
        setLoading(false);
        final CategoryModel categoryModel = response.getData();
        if (!response.isSuccess() || categoryModel == null) {
            setErrorMessage("Lỗi load các thương hiệu");
        }

        final List<BrandModel> brandModels = categoryModel != null ? categoryModel.getCategory() : null;
        if (!AppUtils.checkListHasData(brandModels)) {
            setErrorMessage("Lỗi load các thương hiệu");
            return;
        }

        this.brandModels.setValue(brandModels);
    }

    private void handlerBannerResponse(BannerResponse response) {
        setLoading(false);
        final BannerObject bannerObject = response.getData();
        if (!response.isSuccess() || bannerObject == null) {
            setErrorMessage("Lỗi load các thương hiệu");
        }

        final List<BannerModel> bannerModels = bannerObject != null ? bannerObject.getBanners() : null;
        if (!AppUtils.checkListHasData(bannerModels)) {
            setErrorMessage("Lỗi load các thương hiệu");
            return;
        }

        this.bannerModels.setValue(bannerModels);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
