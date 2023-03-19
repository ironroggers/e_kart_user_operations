package com.innovapptive.useroperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnimationActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ImageView checkmarkImageView = findViewById(R.id.animation_image);
        checkmarkImageView.setImageResource(R.drawable.ic_baseline_check);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.check_animation);
        checkmarkImageView.startAnimation(animation);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended, start the next activity
                databaseReference = firebaseDatabase.getReference("cart");
                databaseReference.removeValue();

                Intent intent = new Intent(AnimationActivity.this, ContinueShoppingActivity.class);
                startActivity(intent);
                // finish the current activity to prevent going back to it with back button
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated
            }
        });


    }
}