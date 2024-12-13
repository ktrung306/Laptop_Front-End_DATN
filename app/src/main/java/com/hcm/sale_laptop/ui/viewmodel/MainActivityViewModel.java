package com.hcm.sale_laptop.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hcm.base.BaseRepository;
import com.hcm.base.BaseViewModel;

public class MainActivityViewModel extends BaseViewModel<BaseRepository> {

    private final MutableLiveData<Boolean> isBottomNavVisible = new MutableLiveData<>(true);

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Boolean> isBottomNavVisible() {
        return isBottomNavVisible;
    }

    public void setBottomNavVisibility(boolean isVisible) {
        isBottomNavVisible.setValue(isVisible);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}