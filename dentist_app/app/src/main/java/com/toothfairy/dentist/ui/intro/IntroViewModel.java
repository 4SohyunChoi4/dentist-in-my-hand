package com.toothfairy.dentist.ui.intro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntroViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IntroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("숙");
    }

    public LiveData<String> getText() {
        return mText;
    }
}