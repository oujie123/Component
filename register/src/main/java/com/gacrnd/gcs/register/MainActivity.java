package com.gacrnd.gcs.register;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arouter_annotations.ARouter;
import com.example.arouter_annotations.Parameter;
import com.gacrnd.gcs.arouter_api.ParameterManager;

@ARouter(path = "/register/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Parameter
    boolean sex;

    @Parameter
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Intent intent = getIntent();
//        Toast.makeText(this,"-->" + intent.getStringExtra("name"),Toast.LENGTH_LONG).show();

        ParameterManager.getInstance().loadParameter(this);
        Toast.makeText(this,"-->name:" + name + ",sex_man:" + sex,Toast.LENGTH_LONG).show();
    }
}
