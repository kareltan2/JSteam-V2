package com.example.traveladvisory.ui.wish_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WishSectionModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WishSectionModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wishlist Section");
    }

    public LiveData<String> getText() {
        return mText;
    }
}