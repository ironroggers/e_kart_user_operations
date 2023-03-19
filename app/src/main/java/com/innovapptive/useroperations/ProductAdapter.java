package com.innovapptive.useroperations;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kotlinx.coroutines.channels.ProduceKt;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    Context c;
    ArrayList<Product> productArrayList;

    public ProductAdapter(Context c, ArrayList<Product> productArrayList) {
        this.c = c;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.product_list_item,parent,false);
        return new ProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.productTitle.setText(product.getProductTitle());
        holder.mrp.setText("₹ "+product.getMrp());
        holder.sp.setText("₹ "+product.getSp());
        holder.category.setText("Category : "+product.getCategory());
        Glide.with(c).load(product.getUrl()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), ProductDetails.class);
                in.putExtra("Product Id", product.getId());
                v.getContext().startActivity(in);
            }
        });
    }



    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, mrp,sp,category;
        ImageView productImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.product_title);
            mrp = itemView.findViewById(R.id.product_mrp);
            sp = itemView.findViewById(R.id.product_sp);
            mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            category = itemView.findViewById(R.id.product_category);
            productImage = itemView.findViewById(R.id.product_list_image);
        }
    }
}
