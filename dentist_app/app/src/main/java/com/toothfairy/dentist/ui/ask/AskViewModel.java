package com.toothfairy.dentist.ui.ask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("치과소개");
    }

    public LiveData<String> getText() {
        return mText;
    }
}