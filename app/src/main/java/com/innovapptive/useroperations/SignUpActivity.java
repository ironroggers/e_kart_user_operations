package com.innovapptive.useroperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, phone, password, confirmPassword;
    Button register;
    TextView alreadyAUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        register = findViewById(R.id.btn_register);
        alreadyAUser = findViewById(R.id.already_user);
        register.setOnClickListener(this);
        alreadyAUser.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                // Add code to send data to database
                String nameVal = name.getText().toString();
                String emailVal = email.getText().toString();
                String phoneVal = phone.getText().toString();
                String passwordVal = password.getText().toString();
                String confirmPasswordVal = confirmPassword.getText().toString();
                if(TextUtils.isEmpty(nameVal) && TextUtils.isEmpty(emailVal) && TextUtils.isEmpty(phoneVal) && TextUtils.isEmpty(passwordVal) && TextUtils.isEmpty(confirmPasswordVal)){
                    Toast.makeText(SignUpActivity.this, "Insert Data", Toast.LENGTH_SHORT).show();
                }else if(!passwordVal.equals(confirmPasswordVal)){
                    Toast.makeText(this, "Confirm password not matching", Toast.LENGTH_SHORT).show();
                }else{

                    addDataToFirebase(nameVal,emailVal,phoneVal,passwordVal);
                    firebaseAuth.createUserWithEmailAndPassword(emailVal,passwordVal)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(SignUpActivity.this, SignInActivity.class);
                                        startActivity(in);
                                    }else{
                                        Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                break;
            case R.id.already_user:
                Intent in2 = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(in2);
                break;
        }
    }
    Map<String, Object> map = new HashMap<>();

    private void addDataToFirebase(String nameVal, String emailVal, String phoneVal, String passwordVal) {
        UserCredentials userCredentials = new UserCredentials(nameVal,emailVal,phoneVal,passwordVal);
        map.put(phoneVal,userCredentials);
        databaseReference.setValue(map);
        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
    }
}