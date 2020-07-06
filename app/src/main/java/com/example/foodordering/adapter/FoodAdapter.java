package com.example.foodordering.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodordering.model.favorite.FavoriteId;
import com.example.foodordering.model.food.Food;
import com.example.foodordering.model.eventbus.FoodDetailEvent;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.ui.view.FoodDetail;
import com.example.foodordering.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>  {
    Context context;
    LayoutInflater inflater;
    List<Food> foodList;
    CompositeDisposable compositeDisposable;
    CartDataSource cartDataSource;
    IMyRestaurantAPI myRestaurantAPI;

    public void onStop() {
        this.compositeDisposable.clear();
    }

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.compositeDisposable = new CompositeDisposable();
        this.cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());
        this.myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
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

        //Check favorite
        if(Utils.currentFavOfRestaurant != null && Utils.currentFavOfRestaurant.size() > 0) {
            if(Utils.checkFavorite(foodList.get(position).getID())) {
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
                holder.btnFavorite.setTag(true);
            } else {
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                holder.btnFavorite.setTag(false);
            }
        } else {
            //Default all item is no favorite
            holder.btnFavorite.setTag(false);
        }

        //Event
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView fav = (ImageView) v;
                if((Boolean) fav.getTag()) {
                    compositeDisposable.add(myRestaurantAPI.removeFavorite("Bearer "+Utils.currentUser.getToken(),
                            Utils.API_KEY,
                            Utils.currentUser.getEmail(),
                            foodList.get(position).getID(),
                            Utils.currentRestaurant.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    favoriteModel -> {
                                        if(favoriteModel.isSuccess()) {
                                            Log.d("DELETE", favoriteModel.toString());
                                            fav.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                                            fav.setTag(false);
                                            if(Utils.currentFavOfRestaurant != null) {
                                                Log.d("DELETE BEFORE REMOVE", Utils.currentFavOfRestaurant.size() + Utils.currentFavOfRestaurant.get(0).getFoodId() + "");
                                                Utils.removeFavorite(foodList.get(position).getID());
                                                Log.d("DELETE BEFORE REMOVE", Utils.currentFavOfRestaurant.size() + Utils.currentFavOfRestaurant.get(0).getFoodId() + "");

                                            }
                                        } else {
                                            Log.d("DELETE", favoriteModel.toString());
                                        }
                                    },
                                    throwable -> {Log.d("FAV", throwable.getMessage());})
                    );
                } else {
                    compositeDisposable.add(
                                myRestaurantAPI.insertFavorite("Bearer "+ Utils.currentUser.getToken(),
                                Utils.API_KEY,
                                Utils.currentUser.getEmail(),
                                foodList.get(position).getID(),
                                Utils.currentRestaurant.getId(),
                                Utils.currentRestaurant.getName(),
                                foodList.get(position).getName(),
                                foodList.get(position).getImage(),
                                foodList.get(position).getPrice()
                            )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    favoriteModel -> {
                                        if(favoriteModel.isSuccess()) {
                                            fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                                            fav.setTag(true);
                                            if(Utils.currentFavOfRestaurant != null) {
                                                Utils.currentFavOfRestaurant.add(new FavoriteId(foodList.get(position).getID()));
                                                Log.d("BBB", Utils.currentFavOfRestaurant.get(Utils.currentFavOfRestaurant.size()-1).getFoodId()+"");
                                            }
                                        }
                                    },
                                    throwable -> {Log.d("FAV", throwable.getMessage());})
                    );
                }
            }
        });
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
                    cartItem.setUserPhone(Utils.currentUser.getUserPhone());
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
                    context.startActivity(new Intent(context, FoodDetail.class));
                    EventBus.getDefault().postSticky(new FoodDetailEvent(true, foodList.get(position)));
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
        @BindView(R.id.img_favorite) ImageView btnFavorite;
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
