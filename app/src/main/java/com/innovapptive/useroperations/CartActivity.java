package com.innovapptive.useroperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView cartRecyclerView;
    TextView cartPrice;
    Button proceedToBuy;
    LinearLayout linearLayout;
    CartAdapter cartAdapter;
    ArrayList<CartProduct> cartArrayList;
    ConstraintLayout constraintLayout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        proceedToBuy = findViewById(R.id.proceed_to_buy);
        cartPrice = findViewById(R.id.cart_price);
        linearLayout = findViewById(R.id.empty_layout);
        cartArrayList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartArrayList.clear();
                int price=0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartProduct cartProduct = dataSnapshot.getValue(CartProduct.class);
                    cartArrayList.add(cartProduct);
                    price+=Integer.parseInt(cartProduct.getSp())*Integer.parseInt(cartProduct.getQuantity());
                    cartRecyclerView.setAdapter(cartAdapter);
                }
                if(cartArrayList.isEmpty()){
                    linearLayout.setBackground(getApplicationContext().getDrawable(R.drawable.ic_baseline_empty_cart));
                }
                cartPrice.setText(String.valueOf(price));
                proceedToBuy.setText("Proceed to buy( "+cartArrayList.size()+" items)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        proceedToBuy.setOnClickListener(this);



        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartArrayList);
        cartRecyclerView.setAdapter(cartAdapter);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.proceed_to_buy){
            if(cartArrayList.isEmpty()){
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            }else{
                cartArrayList.clear();
                Intent in = new Intent(CartActivity.this, AnimationActivity.class);
                startActivity(in);
            }

        }

    }
}