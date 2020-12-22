package com.gacrnd.gcs.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arouter_annotations.ARouter;
import com.gacrnd.gcs.arouter_api.RouterManager;

@ARouter(path = "/login/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 类加载的方式跳转
//                try {
//                    Class activityClass = Class.forName("com.gacrnd.gcs.register.MainActivity");
//                    Intent intent = new Intent(MainActivity.this,activityClass);
//                    intent.putExtra("name","JackOu");
//                    startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

                RouterManager.getInstance().build("/register/MainActivity")
                        .withBoolean("sex",true)
                        .withString("name","jack")
                        .navigation(MainActivity.this);
            }
        });
    }
}
