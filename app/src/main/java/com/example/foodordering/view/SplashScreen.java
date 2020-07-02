package com.example.foodordering.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.foodordering.R;

public class SplashScreen extends AppCompatActivity {
    private ImageView imgLogo;


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in);
            imgLogo.setAnimation(animation);
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initView();

        handler.postDelayed(runnable, 2000);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        imgLogo.setAnimation(animation);


    }
    private void initView() {
        imgLogo = findViewById(R.id.img_logo);
    }
}
