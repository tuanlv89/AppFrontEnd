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

import com.example.foodordering.Interface.IOnRecyclerViewClickListener;
import com.example.foodordering.R;
import com.example.foodordering.model.eventbus.FoodDetailEvent;
import com.example.foodordering.model.favorite.Favorite;
import com.example.foodordering.model.restaurant.Restaurant;
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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Favorite> favoriteList;
    CompositeDisposable compositeDisposable;
    IMyRestaurantAPI myRestaurantAPI;

    public void onStop() {
        this.compositeDisposable.clear();
    }

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.compositeDisposable = new CompositeDisposable();
        this.myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(favoriteList.get(position).getFoodImage()).into(holder.imgFood);
        holder.tvFoodName.setText(favoriteList.get(position).getFoodName());
        holder.tvFoodPrice.setText(favoriteList.get(position).getPrice() + " VND");
        holder.tvRestaurantName.setText(favoriteList.get(position).getRestaurantName());

        holder.setListener((view, position1) -> {
                Log.d("AAA", "CLICK");
                compositeDisposable.add(
                        myRestaurantAPI.getFoodById(Utils.API_KEY, favoriteList.get(position1).getFoodId(), "Bearer "+Utils.currentUser.getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        foodModel -> {
                                            if(foodModel.isSuccess()) {
                                                Log.d("AAA", "TEST");
                                                context.startActivity(new Intent(context, FoodDetail.class));
                                                if(Utils.currentFavOfRestaurant == null)
                                                    Utils.currentRestaurant = new Restaurant();
                                                Utils.currentRestaurant.setId(favoriteList.get(position1).getRestaurantId());
                                                Utils.currentRestaurant.setName(favoriteList.get(position1).getRestaurantName());
                                                EventBus.getDefault().postSticky(new FoodDetailEvent(true, foodModel.getResult().get(0)));

                                            } else {
                                                Log.d("AAA", foodModel.getMessage());
                                                Toast.makeText(context, foodModel.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        },
                                        throwable -> {
                                            Log.d("AAA", throwable.getMessage());
                                        }
                                )
                );
            });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_food)
        ImageView imgFood;
        @BindView(R.id.tv_food_name)
        TextView tvFoodName;
        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;
        @BindView(R.id.tv_restaurant_name)
        TextView tvRestaurantName;

        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCLick(v, getAdapterPosition());
        }
    }
}
