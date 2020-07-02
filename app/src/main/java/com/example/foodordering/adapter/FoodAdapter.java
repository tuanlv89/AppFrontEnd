package com.example.foodordering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.Interface.IOnFoodCartClick;
import com.example.foodordering.R;
import com.example.foodordering.model.Food.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>  {
    Context context;
    LayoutInflater inflater;
    List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(foodList.get(position).getImage()).into(holder.imgFood);
        holder.tvFoodName.setText(foodList.get(position).getName());
        holder.tvFoodPrice.setText(foodList.get(position).getPrice() + " VND");

        holder.setListener(new IOnFoodCartClick() {
            @Override
            public void onFoodItemCLickListener(View view, int position, boolean isCart) {
                if(isCart) {
                    Toast.makeText(context, "Cart click", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Item click", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_food) ImageView imgFood;
        @BindView(R.id.tv_food_name) TextView tvFoodName;
        @BindView(R.id.tv_food_price) TextView tvFoodPrice;
        @BindView(R.id.img_cart) ImageView btnCart;
        IOnFoodCartClick listener;

        public void setListener(IOnFoodCartClick listener) {
            this.listener = listener;
        }

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            btnCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.img_cart) {
                listener.onFoodItemCLickListener(v, getAdapterPosition(), true);
            } else listener.onFoodItemCLickListener(v, getAdapterPosition(), false);
        }
    }
}
