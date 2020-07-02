package com.example.foodordering.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodordering.model.Restaurant.Restaurant;
import com.example.foodordering.model.eventbus.MenuItemEvent;
import com.example.foodordering.ui.view.Menu;
import com.example.foodordering.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
    List<Restaurant> restaurantList;
    LayoutInflater inflater;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(restaurantList.get(position).getImage()).into(holder.imgRestaurant);
        holder.tvRestaurantName.setText(new StringBuilder(restaurantList.get(position).getName()));
        holder.tvRestaurantAddress.setText(new StringBuilder(restaurantList.get(position).getAddress()));

        holder.setListener((view, position1) -> {
            Toast.makeText(context, restaurantList.get(position1).getName(), Toast.LENGTH_LONG).show();
            Utils.currentRestaurant = restaurantList.get(position);
            EventBus.getDefault().postSticky(new MenuItemEvent(true, restaurantList.get(position))); //Save this event in cache and can be listened from other activity
            context.startActivity(new Intent(context, Menu.class));
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_restaurant_name)
        TextView tvRestaurantName;
        @BindView(R.id.tv_restaurant_address)
        TextView tvRestaurantAddress;
        @BindView(R.id.img_restaurant)
        ImageView imgRestaurant;

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
