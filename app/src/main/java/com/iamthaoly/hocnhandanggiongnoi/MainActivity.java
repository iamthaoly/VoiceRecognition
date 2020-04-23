package com.iamthaoly.hocnhandanggiongnoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import com.iamthaoly.hocnhandanggiongnoi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    HashMap<String, String> dictionary = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeDictionary();
        addEvents();
    }

    private void makeDictionary() {
        dictionary.put("school", "trường học");
        dictionary.put("bag", "cái túi");
        dictionary.put("cat", "con mèo");
        dictionary.put("computer", "máy tính");
        dictionary.put("build", "xây dựng");
        dictionary.put("project", "dự án");

    }

    private void addEvents() {
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoi();
            }
        });
        binding.btnTaoTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoTuNgauNhien();
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WithoutDialogue.class);
                startActivity(intent);
            }
        });
    }

    private void taoTuNgauNhien() {
        Random rad = new Random();
        int index = rad.nextInt(dictionary.size());
        String word = (String) dictionary.keySet().toArray()[index];
        binding.txtTuNgauNhien.setText(word);
    }

    private void xuLyGiongNoi() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please talk!");

        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Not supported", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 113 && resultCode == RESULT_OK && data != null)
        {
            //phan tu dau tien la dung nhat.
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //txtSpeechInput.setText(result.get(0));
            binding.txtResult.setText(result.get(0));
        }
    }
}
