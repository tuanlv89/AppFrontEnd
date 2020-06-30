package com.example.foodordering.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodordering.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    CardView panel;
    TextInputLayout edtEmail, edtPassword;
    TextView btnSignIn, btnFacebook;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();

        panel.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        close.setOnClickListener(this);
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
        }

    }


}
