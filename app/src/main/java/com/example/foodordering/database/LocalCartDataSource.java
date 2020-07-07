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
    public Flowable<List<CartItem>> getAllCart(String email, int restaurantId) {
        return cartDAO.getAllCart(email, restaurantId);
    }

    @Override
    public Single<Integer> countItemInCart(String email, int restaurantId) {
        Log.d("SSS", cartDAO.countItemInCart(email, restaurantId)+"");
        return cartDAO.countItemInCart(email, restaurantId);
    }

    @Override
    public Single<Long> sumPrice(String email, int restaurantId) {
        return cartDAO.sumPrice(email, restaurantId);
    }

    @Override
    public Single<CartItem> getItemInCart(String foodId, String email, int restaurantId) {
        return cartDAO.getItemInCart(foodId, email, restaurantId);
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
    public Single<Integer> cleanCart(String email, int restaurantId) {
        return cartDAO.cleanCart(email, restaurantId);
    }
}
