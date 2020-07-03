package com.example.foodordering.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart WHERE userPhone=:userPhone AND restaurantId=:restaurantId")
    Flowable<List<CartItem>> getAllCart(String userPhone, int restaurantId);

    @Query("SELECT COUNT(*) FROM Cart WHERE userPhone=:userPhone AND restaurantId=:restaurantId")
    Single<Integer> countItemInCart(String userPhone, int restaurantId);

    @Query("SELECT * FROM Cart WHERE foodId=:foodId AND userPhone=:userPhone AND restaurantId=:restaurantId")
    Single<CartItem> getItemInCart(String foodId, String userPhone, int restaurantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //if conflict foodId, it will update info
    Completable insertOrReplaceAll(CartItem... cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCart(CartItem cartItem);

    @Delete
    Single<Integer> deleteCart(CartItem cartItem);

    @Query("DELETE FROM Cart WHERE userPhone=:userPhone AND restaurantId=:restaurantId")
    Single<Integer> cleanCart(String userPhone, int restaurantId);

}
