package com.hcm.sale_laptop.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseResponse;
import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.data.model.network.request.OrderRequest;
import com.hcm.sale_laptop.data.repository.PaymentsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentsViewModel extends BaseViewModel<PaymentsRepository> {

    private final MutableLiveData<Boolean> isOrderSuccess = new MutableLiveData<>();

    public PaymentsViewModel() {
        super();
        mRepository = new PaymentsRepository();
    }

    public LiveData<Boolean> getIsOrderSuccess() {
        return isOrderSuccess;
    }

    public void order(OrderRequest request) {
        final Disposable disposable = mRepository.order(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerOrderResponse, throwable -> setErrorMessage(throwable.getMessage()));
        addDisposable(disposable);
    }

    private void handlerOrderResponse(BaseResponse<Object> response) {
        setLoading(false);
        if (response.isSuccess()) {
            setSuccessMessage("Bạn đã đặt đơn hàng thành công");
        } else {
            setErrorMessage("Đặt đơn hàng thất bại");
        }
        this.isOrderSuccess.setValue(response.isSuccess());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
