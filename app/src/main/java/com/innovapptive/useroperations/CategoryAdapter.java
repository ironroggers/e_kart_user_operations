package com.innovapptive.useroperations;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, ArrayList<Category> categoryArrayList) {
        super(context, 0, categoryArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.category_item_list, parent, false);
        }

        Category category = getItem(position);
        TextView categoryName = listitemView.findViewById(R.id.category_name);
        ImageView categoryImage = listitemView.findViewById(R.id.category_image);

        categoryName.setText(category.getName());
        Glide.with(getContext()).load(category.getUrl()).into(categoryImage);
        return listitemView;
    }
}
