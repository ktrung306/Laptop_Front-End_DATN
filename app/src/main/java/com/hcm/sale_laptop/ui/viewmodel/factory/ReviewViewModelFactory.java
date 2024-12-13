package com.hcm.sale_laptop.ui.viewmodel.factory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.ui.viewmodel.ReviewViewModel;

public class ReviewViewModelFactory implements ViewModelProvider.Factory {
    private final ApiService apiService;

    public ReviewViewModelFactory(ApiService apiService) {
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReviewViewModel.class)) {
            return (T) new ReviewViewModel(apiService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

