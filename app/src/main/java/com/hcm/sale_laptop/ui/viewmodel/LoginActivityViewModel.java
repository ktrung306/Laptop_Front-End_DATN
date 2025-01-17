package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseViewModel;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.model.network.request.LoginRequest;
import com.hcm.sale_laptop.data.model.network.response.LoginResponse;
import com.hcm.sale_laptop.data.model.other.UserModel;
import com.hcm.sale_laptop.data.repository.LoginRepository;
import com.hcm.sale_laptop.utils.AppUtils;
import com.hcm.sale_laptop.utils.Constants;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivityViewModel extends BaseViewModel<LoginRepository> {

    private final MutableLiveData<UserModel> userModel = new MutableLiveData<>();

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new LoginRepository();
    }

    public void login(String username, String password) {
        if (AppUtils.stringNullOrEmpty(username)) {
            setErrorMessage(getStringResource(R.string.username_cannot_be_empty));
            return;
        }
        if (AppUtils.stringNullOrEmpty(password) || !isPasswordValid(password)) {
            setErrorMessage(getStringResource(R.string.password_cannot_be_empty));
            return;
        }
        if (!isUserNameValid(username)) {
            setErrorMessage(getStringResource(R.string.username_cannot_email));
            return;
        }
        if (!username.contains("@") && (username.length() < 9 || username.length() > 11)) {
            setErrorMessage(getStringResource(R.string.username_cannot_email));
            return;
        }
        final Disposable disposable = mRepository.login(new LoginRequest(username, password))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dis -> setLoading(true))
                .doOnError(error -> setLoading(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLoginResponse, throwable -> setErrorMessage(throwable.getMessage())
                );
        addDisposable(disposable);
    }

    private void handleLoginResponse(LoginResponse response) {
        setLoading(false);
        if (!response.isSuccess()) {
            setErrorMessage("Đăng nhập không thành công");
            Log.e("LoginActivityViewModel", "handleLoginResponse:" + response.getErrors());
            return;
        }
        final UserModel model = response.getData();
        if (model == null) {
            setErrorMessage("Đăng nhập không thành công");
            Log.e("LoginActivityViewModel", "handleLoginResponse:" + response.getErrors());
            return;
        }
        model.setRoleUser(model.getRole());
        Constants.setUserModel(model);
        Constants.setToken(model.getToken());
        userModel.setValue(model);
    }

    public LiveData<UserModel> getUserModelWhenLoginSuccess() {
        return userModel;
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
        return Patterns.PHONE.matcher(username).matches();

    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 4;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
