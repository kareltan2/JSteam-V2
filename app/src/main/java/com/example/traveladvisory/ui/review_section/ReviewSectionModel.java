package com.example.traveladvisory.ui.review_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewSectionModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReviewSectionModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wishlist Section");
    }

    public LiveData<String> getText() {
        return mText;
    }
}