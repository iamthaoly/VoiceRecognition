package com.iamthaoly.hocnhandanggiongnoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import com.iamthaoly.hocnhandanggiongnoi.databinding.ActivityWithoutDialogueBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WithoutDialogue extends AppCompatActivity implements RecognitionListener {
    ActivityWithoutDialogueBinding binding;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithoutDialogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        addEvents();
    }

    private void addEvents() {
        binding.btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnSpeak.setText("Nghe nè...");
                speech.stopListening();
                speech.startListening(recognizerIntent);

            }
        });
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for(String result : matches)
            text += result + "\n";
        binding.txtKetQua.setText(text);
        text = text.toLowerCase();
        if (text.contains("tắt phần mềm") || text.contains("đóng phần mềm"))
        {
            finish();
            System.exit(0);
        }
        changeColor(text);
        binding.btnSpeak.setText("Nhấn vào để nói tiếng việt");

    }

    private void changeColor(String text) {
        String color;
        binding.txtColor.setTextColor(Color.RED);
        if(text.contains("màu đỏ")) binding.txtColor.setTextColor(Color.RED);
        if(text.contains("màu vàng")) binding.txtColor.setTextColor(Color.YELLOW);
        if(text.contains("màu tím")) binding.txtColor.setTextColor(Color.MAGENTA);
        if(text.contains("màu xanh lá")) binding.txtColor.setTextColor(Color.GREEN);
        if(text.contains("màu xanh dương")) binding.txtColor.setTextColor(Color.BLUE);
        if(text.contains("màu đen")) binding.txtColor.setTextColor(Color.BLACK);
        if(text.contains("màu trắng")) binding.txtColor.setTextColor(Color.WHITE);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }
}
