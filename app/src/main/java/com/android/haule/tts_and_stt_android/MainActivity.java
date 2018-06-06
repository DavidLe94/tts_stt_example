package com.android.haule.tts_and_stt_android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener{
    //take an integer variable for SPEECH and intiate as 1
    protected static final int RESULT_SPEECH = 1;
    //Take a class TextToSpeech for using of Text To Speech
    private TextToSpeech tts;
    private boolean mBack;
    //declare variable
    @BindView(R.id.textView) TextView txtText;
    @BindView(R.id.btnSpeak) Button btnSpeak;
    @BindView(R.id.btnRecord) Button btnRecord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        setEvent();
    }

    private void setEvent() {
        btnRecord.setOnClickListener(this);
        btnSpeak.setOnClickListener(this);
    }

    private void init() {
        /** Interface definition of a callback to be invoked indicating the completion of the TextToSpeech engine initialization. */
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    /** Write the code for the “onActivityResult” method so that when you speak something it will be converted into text automatically and vice versa. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check the requestCode as a case
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    final ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtText.setText(text.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSpeak:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //Get the value using Intent from Speech
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "vi-VN");
                try{
                    startActivityForResult(intent, RESULT_SPEECH);
                    //Set the Text as Empty
                    txtText.setText("");
                }catch (ActivityNotFoundException a) {
                    ShowMessage.getInstance().message(findViewById(R.id.activity), getResources().getString(R.string.message));
                }
                break;
            case R.id.btnRecord:
                //use the TextToSpeech class for converting the Text to Speech
                tts.speak(txtText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //quit app
        if (mBack){
            finish();
            return;
        }
        mBack = true;
        ShowMessage.getInstance().message(findViewById(R.id.activity), getResources().getString(R.string.press_back_again_to_exit));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBack = false;
            }
        },2000);
    }
}