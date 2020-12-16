package com.gacrnd.gcs.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arouter_annotations.ARouter;

@ARouter(path = "/register/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        Toast.makeText(this,"-->" + intent.getStringExtra("name"),Toast.LENGTH_LONG).show();
    }
}
