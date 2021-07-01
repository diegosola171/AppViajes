package com.example.agenciaviajes.ui.viajeContratado;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViajeContratadoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ViajeContratadoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is viajeContratado fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}