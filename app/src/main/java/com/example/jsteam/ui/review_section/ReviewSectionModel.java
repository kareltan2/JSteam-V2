package com.example.jsteam.ui.review_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewSectionModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReviewSectionModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Review Section");
    }

    public LiveData<String> getText() {
        return mText;
    }
}