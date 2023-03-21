package com.innovapptive.useroperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    RecyclerView categoryGridView;
    CategoryAdapter categoryAdapter;
    CatAdapter catAdapter;
    ProductAdapter productAdapter;
    ArrayList<Category> categoryArrayList;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    ArrayList<Product> productArrayList;
    RecyclerView productRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseCategoryReference, databaseProductReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryGridView = findViewById(R.id.grid_view);
        productRecyclerView = findViewById(R.id.recycler_view);
        categoryArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();

//        categoryAdapter = new CategoryAdapter(this,categoryArrayList);
//        categoryGridView.setAdapter(categoryAdapter);
        RecyclerViewLayoutManager = new LinearLayoutManager(this);
        categoryGridView.setLayoutManager(RecyclerViewLayoutManager);
        catAdapter = new CatAdapter(this,categoryArrayList);
        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoryGridView.setLayoutManager(HorizontalLayout);
        categoryGridView.setAdapter(catAdapter);

        //Get the names of Category here into the arraylist.
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseCategoryReference = firebaseDatabase.getReference("categories2");

        databaseCategoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                categoryArrayList.add(new Category("Clear Filters","5000760", Uri.parse("android.resource://com.innovapptive.useroperations/" + R.drawable.clearfilters).toString()));
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    categoryArrayList.add(category);
                    categoryGridView.setAdapter(catAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this,productArrayList);
        productRecyclerView.setAdapter(productAdapter);

        databaseProductReference = firebaseDatabase.getReference("products2");
        databaseProductReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    productArrayList.add(product);
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        catAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                filterByCategory(categoryArrayList.get(position).getName());
            }
        });

//        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                filterByCategory(categoryArrayList.get(position).getName());
//                Log.d("Category Filter", categoryArrayList.get(position).getName());
//            }
//        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_cart_option:
                Intent cartIntent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(cartIntent);
                break;

        }
        return (super.onOptionsItemSelected(item));
    }
    public void filterByCategory(String filterCriteria){
        databaseProductReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    if(filterCriteria.equals("Clear Filters")){
                        productArrayList.add(product);
                    }
                    else if(product.getCategory().equals(filterCriteria)){
                        productArrayList.add(product);
                    }
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryGridView.setAdapter(catAdapter);

        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}