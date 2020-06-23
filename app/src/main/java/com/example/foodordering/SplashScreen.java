package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rellay1, rellay2;
    private ImageView imgLogo;
    private TextInputLayout edtEmail, edtPasswd;
    private Button btnLogIn;
    private TextView tvforgotPass, tvSignUp;


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_in);
            imgLogo.setAnimation(animation);
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
            rellay1.setAnimation(AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_in));
            rellay2.setAnimation(AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_in));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initView();

        handler.postDelayed(runnable, 3000);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        imgLogo.setAnimation(animation);


        //Event click
        btnLogIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvforgotPass.setOnClickListener(this);
    }
    private void initView() {
        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        edtEmail = findViewById(R.id.input_log_email);
        edtPasswd = findViewById(R.id.input_log_pass);
        btnLogIn = findViewById(R.id.btn_log);
        imgLogo = findViewById(R.id.img_logo);
        tvSignUp = findViewById(R.id.txt_sign_up);
        tvforgotPass = findViewById(R.id.txt_forgotPass);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_log:

                break;
            case R.id.txt_sign_up:
                Intent intent = new Intent(SplashScreen.this, Register.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.txt_forgotPass:
                // status: not working

                break;
        }
    }

    private void logIn(String email, String passwd) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Starting login...");
        progressDialog.show();


    }
}
