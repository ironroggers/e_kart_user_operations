package com.innovapptive.useroperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class ProductDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView productImage;
    TextView productTitle,productMrp, productSp;
    Spinner productSizeSpinner;
    ImageButton addQuantityInCart, reduceQuantityInCart;
    TextView selectedQuantity;
    Button addToCart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String selectedSize;
    Product product;
    String [] sizes = {"Choose a size","XS","S","M","L","XL","XXL","XXXL"};
    String [] shoeSize = {"Choose a size", "5","5 wide", "5.5", "5.5 wide", "6","6 wide", "6.5", "6.5 wide"
            , "7","7 wide", "7.5", "7.5 wide", "8","8 wide", "8.5", "8.5 wide", "9","9 wide", "9.5", "9.5 wide"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        String id  = intent.getStringExtra("Product Id");
        productImage = findViewById(R.id.product_detail_image);
        productMrp = findViewById(R.id.product_detail_mrp);
        productTitle = findViewById(R.id.product_detail_title);
        productSp = findViewById(R.id.product_detail_sp);
        productSizeSpinner = findViewById(R.id.size_spinner);
        addQuantityInCart = findViewById(R.id.add_quantity);
        reduceQuantityInCart = findViewById(R.id.reduce_quantity);
        selectedQuantity = findViewById(R.id.selected_quantity);
        final int[] i = {0};
        selectedQuantity.setText(String.valueOf(i[0]));

        addToCart = findViewById(R.id.add_to_cart_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("products2").child(id);

        productSizeSpinner.setOnItemSelectedListener(this);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Product.class);
                Glide.with(getApplicationContext()).load(product.getUrl()).into(productImage);
                productMrp.setText("₹"+product.getMrp());
                productSp.setText("₹"+product.getSp());
                productTitle.setText(product.getProductTitle());
                if(product.getCategory().equals("Shoes")){
                    ArrayAdapter ad = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,shoeSize);
                    ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    productSizeSpinner.setAdapter(ad);
                }else{
                    ArrayAdapter ad = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,sizes);
                    ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    productSizeSpinner.setAdapter(ad);
                }
                productMrp.setPaintFlags(productMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addQuantityInCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedQuantity.setText(String.valueOf(++i[0]));
            }
        });
        reduceQuantityInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(selectedQuantity.getText().toString())==0){
                    selectedQuantity.setText(String.valueOf(0));
                }else{
                    selectedQuantity.setText(String.valueOf(--i[0]));
                }

            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference cartReferance = firebaseDatabase.getReference("cart");
//                cartReferance.child(product.getId()).setValue(product);
                CartProduct cartProduct = new CartProduct(product.getProductTitle(), product.getCategory(), product.getMrp(), product.getSp(), product.getId(), product.getUrl(), selectedSize, selectedQuantity.getText().toString());
                cartReferance.child(product.getId()).setValue(cartProduct);
                Intent cartIntent = new Intent(ProductDetails.this,CartActivity.class);
                startActivity(cartIntent);
                Toast.makeText(ProductDetails.this, "Added item to cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (product.getCategory().equals("Shoes")){
            selectedSize = shoeSize[position];
        }else{
            selectedSize = sizes[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}