package com.example.foodordering.database;

import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalCartDataSource implements CartDataSource {
    private CartDAO cartDAO;

    public LocalCartDataSource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public Flowable<List<CartItem>> getAllCart(String userPhone, int restaurantId) {
        return cartDAO.getAllCart(userPhone, restaurantId);
    }

    @Override
    public Single<Integer> countItemInCart(String userPhone, int restaurantId) {
        Log.d("SSS", cartDAO.countItemInCart(userPhone, restaurantId)+"");
        return cartDAO.countItemInCart(userPhone, restaurantId);
    }

    @Override
    public Single<Long> sumPrice(String userPhone, int restaurantId) {
        return cartDAO.sumPrice(userPhone, restaurantId);
    }

    @Override
    public Single<CartItem> getItemInCart(String foodId, String userPhone, int restaurantId) {
        return cartDAO.getItemInCart(foodId, userPhone, restaurantId);
    }

    @Override
    public Completable insertOrReplaceAll(CartItem... cartItems) {
        return cartDAO.insertOrReplaceAll(cartItems);
    }

    @Override
    public Single<Integer> updateCart(CartItem cartItem) {
        return cartDAO.updateCart(cartItem);
    }

    @Override
    public Single<Integer> deleteCart(CartItem cartItem) {
        return cartDAO.deleteCart(cartItem);
    }

    @Override
    public Single<Integer> cleanCart(String userPhone, int restaurantId) {
        return cartDAO.cleanCart(userPhone, restaurantId);
    }
}
