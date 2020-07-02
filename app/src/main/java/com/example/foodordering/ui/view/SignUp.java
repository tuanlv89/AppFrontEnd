package com.example.foodordering.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodordering.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout edtUsername, edtEmail, edtPassword, edtRetypePass;
    TextView btnLogin, btnSignIn;
    ImageView btnClose;
    private String username = "";
    private String email = "";
    private String pass = "";
    private String rePass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();

        btnLogin.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }


    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_enter_in, R.anim.slide_out_left);
    }

    private void signUp() {
        if (edtUsername != null) username = edtUsername.getEditText().toString();
        if (edtEmail != null) email = edtEmail.getEditText().toString();
        if (edtPassword != null) pass = edtPassword.getEditText().toString();
        if (edtRetypePass != null) rePass = edtRetypePass.getEditText().toString();

        if (validateAccount(username, email, pass, rePass)){
            // register

        }
    }

    private boolean validateAccount(String username, String email, String password, String rePassword) {
        edtUsername.setError(null);
        edtEmail.setError(null);
        edtPassword.setError(null);
        edtRetypePass.setError(null);

        if (username.isEmpty()) {
            edtUsername.setError("User name empty");
            return false;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email name empty");
            return false;
        }
        if (!isValidEmail(email)) {
            edtEmail.setError("Invalid email");
            return false;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password empty");
            return false;
        }
        if (password.length() < 6) {
            edtPassword.setError("Password must be at least 6 characters");
            return false;
        }
        if (rePassword.isEmpty()) {
            edtRetypePass.setError("Password empty");
            return false;
        }
        if (rePassword.length() < 6) {
            edtRetypePass.setError("Password must be at least 6 characters");
            return false;
        }
        if(!rePassword.equals(password)) {
            edtRetypePass.setError("Retype password do not match");
            return false;
        }

        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    private void initView() {
        edtEmail = findViewById(R.id.edit_email);
        edtUsername = findViewById(R.id.edit_fullname);
        edtPassword = findViewById(R.id.edit_password);
        edtRetypePass = findViewById(R.id.edit_retype);
        btnLogin = findViewById(R.id.btn_create);
        btnSignIn = findViewById(R.id.sign_in);
        btnClose = findViewById(R.id.close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                signUp();
                break;
            case R.id.sign_in:
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
