package com.innovapptive.useroperations;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyView>{

    private ArrayList<Category> categoryArrayList;
    Context context;
    int pos;
    private OnItemClickListener listener;


    public CatAdapter(Context context, ArrayList<Category> categoryArrayList){
        this.categoryArrayList = categoryArrayList;
        this.context=context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_list,parent,false);
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(final MyView holder, @SuppressLint("RecyclerView") final int position){
        pos = position;
        holder.categoryName.setText(categoryArrayList.get(position).getName());
        Glide.with(context).load(categoryArrayList.get(position).getUrl()).into(holder.categoryImage);

    }
    @Override
    public int getItemCount(){
        return categoryArrayList.size();
    }



    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView categoryName;
        ImageView categoryImage;
        LinearLayout categoryCardView;
        public MyView(View view){
            super(view);
            categoryName = (TextView)view.findViewById(R.id.category_name);
            categoryImage = (ImageView)view.findViewById(R.id.category_image);
            view.setOnClickListener(this);

         }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                listener.onItemClick(position);
            }
        }
    }

}