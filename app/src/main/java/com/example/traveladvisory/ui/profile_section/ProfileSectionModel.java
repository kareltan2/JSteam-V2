package com.example.traveladvisory.ui.profile_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileSectionModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfileSectionModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Profile Section");
    }

    public LiveData<String> getText() {
        return mText;
    }
}