package com.gacrnd.gcs.component;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arouter_annotations.ARouter;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = BuildConfig.url;
    }

    public void jump(View view) {
        try {
            Class activityClass = Class.forName("com.gacrnd.gcs.register.MainActivity");
            Intent intent = new Intent(MainActivity.this,activityClass);
            intent.putExtra("name","JackOu");
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
