package com.example.foodordering.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CartDataSource {
    Flowable<List<CartItem>> getAllCart(String userPhone, int restaurantId);

    Single<Integer> countItemInCart(String userPhone, int restaurantId);

    Single<CartItem> getItemInCart(String foodId, String userPhone, int restaurantId);

    Completable insertOrReplaceAll(CartItem... cartItems);

    Single<Integer> updateCart(CartItem cartItem);

    Single<Integer> deleteCart(CartItem cartItem);

    Single<Integer> cleanCart(String userPhone, int restaurantId);
}
