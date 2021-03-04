package com.toothfairy.dentist.intro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntroViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IntroViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}