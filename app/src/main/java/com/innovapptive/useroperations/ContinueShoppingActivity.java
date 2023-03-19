package com.innovapptive.useroperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContinueShoppingActivity extends AppCompatActivity {
    Button continueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_shopping);
        continueShopping = findViewById(R.id.continueShopping);
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ContinueShoppingActivity.this,MainActivity.class);
                startActivity(in);
            }
        });
    }
}