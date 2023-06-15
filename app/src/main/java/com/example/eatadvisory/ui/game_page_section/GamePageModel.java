package com.example.eatadvisory.ui.game_page_section;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GamePageModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GamePageModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Food Page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}