package com.hcm.sale_laptop.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.response.OrderResponse;
import com.hcm.sale_laptop.data.model.other.OrderObject;
import com.hcm.sale_laptop.data.model.other.OrderStateModel;
import com.hcm.sale_laptop.data.repository.OrderRepository;
import com.hcm.sale_laptop.utils.AppUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WaitingDeliveryViewModel extends BaseViewModel<OrderRepository> {

    private final MutableLiveData<List<OrderStateModel>> orderData = new MutableLiveData<>();

    public WaitingDeliveryViewModel() {
        super();
        mRepository = new OrderRepository();
    }

    public LiveData<List<OrderStateModel>> getOrderData() {
        return orderData;
    }

    public void getOrderByUser(String id) {
        final Disposable disposable = mRepository.getOrderByUser(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerOrderResponse, throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void handlerOrderResponse(OrderResponse response) {
        setLoading(false);
        final OrderObject object = response.getData();
        if (!response.isSuccess() || object == null) {
            setErrorMessage("Lỗi load danh sách đơn hàng");
        }

        final List<OrderStateModel> models = object != null ? object.getList() : null;
        if (!AppUtils.checkListHasData(models)) {
            setErrorMessage("Lỗi load danh sách đơn hàng");
            return;
        }

        this.orderData.setValue(models);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
