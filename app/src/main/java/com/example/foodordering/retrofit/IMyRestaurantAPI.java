package com.example.foodordering.retrofit;

import com.example.foodordering.model.Food.FoodModel;
import com.example.foodordering.model.Menu.MenuModel;
import com.example.foodordering.model.Restaurant.RestaurantModel;
import com.example.foodordering.model.User.UpdateUserModel;
import com.example.foodordering.model.User.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @POST("user/login")
    @FormUrlEncoded
    Observable<UserModel> login(@Field("email") String email, @Field("password") String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<UserModel> register(@Field("userPhone") String userPhone,
                                   @Field("name") String name,
                                   @Field("address") String address,
                                   @Field("email")  String email,
                                   @Field("password") String password);

    // RESTAURANT
    @GET("restaurant")
    Observable<RestaurantModel> getAllRestaurant(@Query("key") String apiKey);

    // MENU
    @GET("menu")
    Observable<MenuModel> getCategory(@Query("key") String apiKey, @Query("restaurantId") int restaurantId);

    //FOOD
    @GET("food")
    Observable<FoodModel> getFoodByMenuId(@Query("key") String apiKey, @Query("menuId") int menuId);
}
