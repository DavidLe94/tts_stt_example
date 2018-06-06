package com.android.haule.tts_and_stt_android;

import android.support.design.widget.Snackbar;
import android.view.View;


public class ShowMessage {
    public static ShowMessage instance;

    public static ShowMessage getInstance() {
        if(instance == null){
            instance = new ShowMessage();
        }
        return instance;
    }

    public void message(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}

