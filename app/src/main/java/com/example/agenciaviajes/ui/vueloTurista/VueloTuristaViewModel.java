package com.example.agenciaviajes.ui.vueloTurista;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VueloTuristaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VueloTuristaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is vueloTurista fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}