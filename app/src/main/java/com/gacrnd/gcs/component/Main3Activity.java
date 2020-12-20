package com.gacrnd.gcs.component;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.arouter_annotations.ARouter;

@ARouter(path = "/app/Main3Activity",group = "app")
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}
