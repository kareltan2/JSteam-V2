package com.example.traveladvisory.ui.place_page_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlacePageModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlacePageModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Place Page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}