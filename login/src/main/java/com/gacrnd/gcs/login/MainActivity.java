package com.gacrnd.gcs.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arouter_annotations.ARouter;

//@ARouter(path = "/login/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Class activityClass = Class.forName("com.gacrnd.gcs.register.MainActivity");
                    Intent intent = new Intent(MainActivity.this,activityClass);
                    intent.putExtra("name","JackOu");
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
