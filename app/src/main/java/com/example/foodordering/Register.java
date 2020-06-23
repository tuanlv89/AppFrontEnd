package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private TextInputLayout edtName, edtEmail, edtPasswd;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }
    private void registerNewUser() {
        final String name = edtName.getEditText().getText().toString().trim();
        String email = edtEmail.getEditText().getText().toString().trim();
        String passwd = edtPasswd.getEditText().getText().toString().trim();
        if(name.isEmpty()||email.isEmpty()||passwd.isEmpty()) {
            Snackbar.make(btnRegister, "Please complete all information!", Snackbar.LENGTH_LONG).show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Starting sign up...");
            progressDialog.show();


        }

    }

    private void initView() {
        edtName = findViewById(R.id.input_reg_name);
        edtEmail = findViewById(R.id.input_reg_email);
        edtPasswd = findViewById(R.id.input_reg_pass);
        btnRegister = findViewById(R.id.btn_reg);
    }
}
