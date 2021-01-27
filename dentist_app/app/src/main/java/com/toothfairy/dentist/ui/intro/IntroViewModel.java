package com.toothfairy.dentist.ui.intro;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.toothfairy.dentist.R;

public class IntroViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IntroViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}