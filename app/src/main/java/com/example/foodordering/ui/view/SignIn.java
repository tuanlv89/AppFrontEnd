package com.example.foodordering.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.model.user.User;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    CardView panel;
    TextInputLayout edtEmail, edtPassword;
    TextView btnSignIn, btnFacebook;
    ImageView close;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SharedPreferences sharedPreferences;
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
        initView();
        panel.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        close.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_enter_in, R.anim.slide_out_left);
    }

    private void initView() {
        panel = findViewById(R.id.panel);
        edtEmail = findViewById(R.id.edit_email);
        edtPassword = findViewById(R.id.edit_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnFacebook = findViewById(R.id.btn_facebook);
        close = findViewById(R.id.close);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.panel:
                clickPanel();
                break;
            case R.id.close:
                finish();
                break;
            case R.id.btn_sign_in:
                signInWithForm();
                break;
        }
    }

    private void clickPanel(){
        if(edtPassword.getEditText()!=null)
            edtPassword.getEditText().clearFocus();

        if(edtEmail.getEditText()!=null)
            edtEmail.getEditText().clearFocus();
    }

    private boolean validateAccount(String email, String password){
        edtEmail.setError(null);
        edtPassword.setError(null);

        if(email.isEmpty()){
            edtEmail.setError("Email cannot be empty");
            return false;
        }
        if(!isValidEmail(email)){
            edtEmail.setError("Invalid email");
            return false;
        }
        if(password.isEmpty()){
            edtPassword.setError("Password cannot be empty");
            return false;
        }
        if(password.length()< 6){
            edtPassword.setError("Password must be at least 6 characters");
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

    void signInWithForm() {
        String email="",password="";

        EditText editText = edtEmail.getEditText();

        if(editText != null) email = editText.getText().toString();

        editText = edtPassword.getEditText();
        if(editText != null) password = editText.getText().toString();

        if(validateAccount(email,password)) {
            //login
            ProgressLoading.show(SignIn.this);
            compositeDisposable.add(
                    myRestaurantAPI.login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()) {
                                        String Email = userModel.getResult().get(0).getEmail();
                                        String address = userModel.getResult().get(0).getAddress();
                                        String userPhone = userModel.getResult().get(0).getUserPhone();
                                        String name = userModel.getResult().get(0).getName();
                                        String token = userModel.getToken();
                                        User user = new User(Email, userPhone, address, name, token);
                                        Utils.currentUser = user;
                                        Log.d("AAA", Utils.currentUser.toString());

                                        //---------------------------
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(Utils.EMAIL, Email);
                                        editor.putString(Utils.TOKEN, token);

                                        editor.apply();
                                        //---------------------------
                                        startActivity(new Intent(SignIn.this, MainActivity.class));
                                        finish();
                                    } else Toast.makeText(SignIn.this, userModel.getMessage(), Toast.LENGTH_LONG).show();
                                },
                                throwable -> {
                                    Log.d("ERROR USER", throwable.getMessage());
                                    Toast.makeText(SignIn.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                })
            );
            ProgressLoading.dismiss();
        }

    }
}
