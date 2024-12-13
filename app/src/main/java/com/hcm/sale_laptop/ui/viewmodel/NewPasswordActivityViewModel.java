package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseRepository;
import com.hcm.base.BaseViewModel;

public class NewPasswordActivityViewModel extends BaseViewModel<BaseRepository> {
    private final MutableLiveData<Boolean> isValidEmail = new MutableLiveData<Boolean>();

    public NewPasswordActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public NewPasswordActivityViewModel() {
        super();
    }

    public void requestApiValidEmail(String email) {

    }

    public MutableLiveData<Boolean> getIsValidEmail() {
        return isValidEmail;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
