package com.example.foodordering.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout edtUsername, edtEmail, edtPhone, edtPassword, edtRetypePass;
    TextView btnLogin, btnSignIn;
    ImageView btnClose;
    private String username = "";
    private String email = "";
    private String phone = "";
    private String pass = "";
    private String rePass = "";

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
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
        if (edtUsername != null) username = edtUsername.getEditText().getText().toString();
        if (edtEmail != null) email = edtEmail.getEditText().getText().toString();
        if (edtPhone != null) phone = edtPhone.getEditText().getText().toString();
        if (edtPassword != null) pass = edtPassword.getEditText().getText().toString();
        if (edtRetypePass != null) rePass = edtRetypePass.getEditText().getText().toString();

        if (validateAccount(username, email, phone, pass, rePass)){
            // register
            ProgressLoading.show(SignUp.this);
            compositeDisposable.add(
                    myRestaurantAPI.register(phone, username, "", email, pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()) {
                                            Toast.makeText(SignUp.this, "Đăng ký thành công! Vui lòng đăng nhập để tiếp tục", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignUp.this, SignIn.class));
                                            finish();
                                        } else {
                                            Toast.makeText(SignUp.this, userModel.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    throwable -> {
                                        Log.d("ERROR REGISTER", throwable.getMessage());
                                        Toast.makeText(SignUp.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    })
            );
            ProgressLoading.dismiss();
        }
    }

    private boolean validateAccount(String username, String email, String phone, String password, String rePassword) {
        edtUsername.setError(null);
        edtEmail.setError(null);
        edtPhone.setError(null);
        edtPassword.setError(null);
        edtRetypePass.setError(null);

        if (username.trim().equals("")) {
            edtUsername.setError("User name empty");
            return false;
        }
        if (email.trim().equals("")) {
            edtEmail.setError("Email name empty");
            return false;
        }
        if (!isValidEmail(email)) {
            edtEmail.setError("Invalid email");
            return false;
        }
        if(phone.trim().equals("")) {
            edtPhone.setError("Phone number empty");
            return false;
        }
        if (password.trim().equals("")) {
            edtPassword.setError("Password empty");
            return false;
        }
        if (password.length() < 6) {
            edtPassword.setError("Password must be at least 6 characters");
            return false;
        }
        if (rePassword.trim().equals("")) {
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
        edtPhone = findViewById(R.id.edit_phone);
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
