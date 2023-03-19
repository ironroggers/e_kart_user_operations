package com.innovapptive.useroperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    TextView forgotPassword, registerNewAccount;
    Button signInButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);
        forgotPassword = findViewById(R.id.forgot_password_text);
        registerNewAccount = findViewById(R.id.new_account_text);
        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        registerNewAccount.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void loginUser(){
        String emailVal, passwordVal;
        emailVal = username.getText().toString();
        passwordVal = password.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(emailVal, passwordVal)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this, "Sign In Successfull", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(in);
                        }else{
                            Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.forgot_password_text:
                Toast.makeText(this, "Try to remember your password", Toast.LENGTH_SHORT).show();
                break;
            case R.id.new_account_text:
                Intent in = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(in);
                break;
            case R.id.btn_sign_in:
                loginUser();
                Intent in2 = new Intent(SignInActivity.this,MainActivity.class);
                startActivity(in2);
                break;
        }
    }
}