package com.gacrnd.gcs.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arouter_annotations.ARouter;
import com.example.arouter_annotations.Parameter;
import com.example.common.register.drawable.RegisterDrawable;
import com.example.common.register.user.IUser;
import com.gacrnd.gcs.arouter_api.ParameterManager;

@ARouter(path = "/login/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Parameter(name = "/register/getDrawable")
    RegisterDrawable registerDrawable;
    private ImageView image;

    @Parameter(name = "/register/getUserInfo")
    IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParameterManager.getInstance().loadParameter(this);
        image = findViewById(R.id.image);
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

//                RouterManager.getInstance().build("/register/MainActivity")
//                        .withBoolean("sex",true)
//                        .withString("name","jack")
//                        .navigation(MainActivity.this);

                int drawable = registerDrawable.getDrawable();
                image.setImageResource(drawable);
            }
        });

        Log.d("jack", "onCreate: " + user.getUserInfo().toString());
    }
}
