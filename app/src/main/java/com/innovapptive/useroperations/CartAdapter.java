package com.innovapptive.useroperations;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    Context cartContext;
    ArrayList<CartProduct> cartArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public CartAdapter(Context cartContext, ArrayList<CartProduct> cartArrayList) {
        this.cartContext = cartContext;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cartContext).inflate(R.layout.cart_list_item,parent,false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        CartProduct product = cartArrayList.get(position);
        holder.productCartTitle.setText(product.getProductTitle());
        holder.cartSp.setText("₹"+product.getSp());
        holder.cartMrp.setText("₹"+product.getMrp());
        holder.cartCategory.setText("Category : "+product.getCategory());
        Glide.with(cartContext).load(product.getUrl()).into(holder.productCartImage);
        holder.productCartQuantity.setText(product.getQuantity());
        holder.productCartSize.setText(product.getSize());
        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(cartContext);
                builder.setMessage("Are you sure you want delete the item from cart ?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("cart");
                    Toast.makeText(cartContext, cartArrayList.get(position).getProductTitle()+" removed from cart", Toast.LENGTH_SHORT).show();
                    databaseReference.child(cartArrayList.get(position).getId()).removeValue();
                    cartArrayList.remove(position);
                    notifyDataSetChanged();

                    dialog.cancel();
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productCartTitle, cartMrp,cartSp,cartCategory,productCartSize,productCartQuantity;
        ImageView productCartImage;
        ImageButton deleteCartItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartImage = itemView.findViewById(R.id.product_cart_image);
            productCartTitle = itemView.findViewById(R.id.product_cart_title);
            cartMrp = itemView.findViewById(R.id.product_cart_mrp);
            cartMrp.setPaintFlags(cartMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            cartSp = itemView.findViewById(R.id.product_cart_sp);
            cartCategory = itemView.findViewById(R.id.product_cart_category);
            productCartSize = itemView.findViewById(R.id.product_cart_size);
            productCartQuantity = itemView.findViewById(R.id.product_cart_quantity);
            deleteCartItem = itemView.findViewById(R.id.delete_cart_item);
        }
    }
}
