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
import com.example.foodordering.database.CartDataSource;
import com.example.foodordering.database.CartDatabase;
import com.example.foodordering.database.CartItem;
import com.example.foodordering.database.LocalCartDataSource;
import com.example.foodordering.model.Food.Food;
import com.example.foodordering.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>  {
    Context context;
    LayoutInflater inflater;
    List<Food> foodList;
    CompositeDisposable compositeDisposable;
    CartDataSource cartDataSource;

    public void onStop() {
        compositeDisposable.clear();
    }

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        compositeDisposable = new CompositeDisposable();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());
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
                    CartItem cartItem = new CartItem();
                    cartItem.setFoodId(foodList.get(position).getID());
                    cartItem.setFoodName(foodList.get(position).getName());
                    cartItem.setFoodPrice(foodList.get(position).getPrice());
                    cartItem.setFoodImage(foodList.get(position).getImage());
                    cartItem.setFoodQuantity(1);
                    cartItem.setUserPhone(Utils.currentRestaurant.getPhone());
                    cartItem.setRestaurantId(Utils.currentRestaurant.getId());
                    cartItem.setFoodAddon("NORMAL");
                    cartItem.setFoodSize("NORMAL");
                    cartItem.setFoodExtraPrice(0.0);

                    compositeDisposable.add(
                            cartDataSource.insertOrReplaceAll(cartItem)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                Toast.makeText(context, "ADDED TO CART", Toast.LENGTH_LONG).show();
                                    },
                                    throwable -> {
                                Log.d("ERROR CART", throwable.getMessage());
                                    })
                    );
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
