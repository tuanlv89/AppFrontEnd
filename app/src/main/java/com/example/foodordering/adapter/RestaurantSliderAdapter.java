package com.example.foodordering.adapter;

import android.util.Log;

import com.example.foodordering.model.restaurant.Restaurant;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class RestaurantSliderAdapter extends SliderAdapter {

    List<Restaurant> restaurantList;

    public RestaurantSliderAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        Log.d("GET RES", restaurantList.get(0).toString());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(restaurantList.get(position).getImage());
    }
}
