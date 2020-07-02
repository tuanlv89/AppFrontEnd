package com.example.foodordering.retrofit;

import com.example.foodordering.model.MenuModel;
import com.example.foodordering.model.RestaurantModel;
import com.example.foodordering.model.UpdateUserModel;
import com.example.foodordering.model.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyRestaurantAPI {

    // USER
    @GET("user")
    Observable<UserModel> getUser(@Query("key") String apiKey, @Query("userPhone") String userPhone);

    @POST("user")
    Observable<UpdateUserModel> updateUserInfo(@Field("key") String apiKey,
                                               @Field("userPhone") String userPhone,
                                               @Field("userName") String userName,
                                               @Field("email") String email);


    // RESTAURANT
    @GET("restaurant")
    Observable<RestaurantModel> getAllRestaurant(@Query("key") String apiKey);

    // MENU
    @GET("menu")
    Observable<MenuModel> getCategory(@Query("key") String apiKey, @Query("restaurantId") int restaurantId);
}
