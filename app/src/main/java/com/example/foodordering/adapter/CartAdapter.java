package com.example.foodordering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.Interface.IOnImageViewClickListener;
import com.example.foodordering.R;
import com.example.foodordering.database.CartDataSource;
import com.example.foodordering.database.CartDatabase;
import com.example.foodordering.database.CartItem;
import com.example.foodordering.database.LocalCartDataSource;
import com.example.foodordering.model.eventbus.CalculateNewPriceEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<CartItem> cartItemList;
    CartDataSource cartDataSource;

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("AAA loi", cartItemList.get(position).toString());
        Picasso.get().load(cartItemList.get(position).getFoodImage()).into(holder.imgFood);
        holder.tvFoodName.setText(cartItemList.get(position).getFoodName());
        holder.tvFoodPrice.setText(cartItemList.get(position).getFoodPrice()+"");
        holder.tvQuantity.setText(cartItemList.get(position).getFoodQuantity()+"");

        double newPrice =  cartItemList.get(position).getFoodPrice() * cartItemList.get(position).getFoodQuantity();
        holder.tvNewPrice.setText(newPrice+" VND");
        holder.tvExtraPrice.setText("Extra Price: +" + cartItemList.get(position).getFoodExtraPrice());

        //Event
        holder.setListener(new IOnImageViewClickListener() {
            @Override
            public void onCalculatePriceListener(View view, int position, boolean isDecrease, boolean isDelete) {
                if(!isDelete) {
                    if(isDecrease) {
                        if(cartItemList.get(position).getFoodQuantity() > 1) {
                            cartItemList.get(position).setFoodQuantity(cartItemList.get(position).getFoodQuantity()-1);
                        }
                    } else {
                        if(cartItemList.get(position).getFoodQuantity() < 100) {
                            cartItemList.get(position).setFoodQuantity(cartItemList.get(position).getFoodQuantity()+1);
                        }
                    }
                    //Update cart
                    cartDataSource.updateCart(cartItemList.get(position))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Integer>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(Integer integer) {
                                        holder.tvQuantity.setText(cartItemList.get(position).getFoodQuantity()+"");
                                        EventBus.getDefault().postSticky(new CalculateNewPriceEvent());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("ERROR", e.getMessage());
                                    }
                                });
                } else {
                    //Delete cart
                    cartDataSource.deleteCart(cartItemList.get(position))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Integer>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Integer integer) {
                                    notifyItemRemoved(position);
                                    EventBus.getDefault().postSticky(new CalculateNewPriceEvent());
                                    if(cartItemList!=null) {
                                        Log.d("AAA list", cartItemList.size()+""+cartItemList.get(0).toString());
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("ERROR", e.getMessage());
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_food) ImageView imgFood;
        @BindView(R.id.tv_food_name) TextView tvFoodName;
        @BindView(R.id.tv_food_price) TextView tvFoodPrice;
        @BindView(R.id.tv_quantity) TextView tvQuantity;
        @BindView(R.id.img_decrease) ImageView imgDecrease;
        @BindView(R.id.img_increase) ImageView imgIncrease;
        @BindView(R.id.tv_food_price_new) TextView tvNewPrice;
        @BindView(R.id.tv_extra_price) TextView tvExtraPrice;
        @BindView(R.id.img_delete_food) ImageView imgDeleteFood;

        IOnImageViewClickListener listener;

        public void setListener(IOnImageViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            imgDecrease.setOnClickListener(this);
            imgIncrease.setOnClickListener(this);
            imgDeleteFood.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_decrease:
                    listener.onCalculatePriceListener(v, getAdapterPosition(), true, false);
                    break;
                case R.id.img_increase:
                    listener.onCalculatePriceListener(v, getAdapterPosition(), false, false);
                    break;
                case R.id.img_delete_food:
                    listener.onCalculatePriceListener(v, getAdapterPosition(), false, true);
                    break;
            }
        }
    }
}
