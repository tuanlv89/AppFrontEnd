package com.example.foodordering.utils;

import android.util.Log;

import com.example.foodordering.model.addOn.AddOn;
import com.example.foodordering.model.favorite.Favorite;
import com.example.foodordering.model.favorite.FavoriteId;
import com.example.foodordering.model.restaurant.Restaurant;
import com.example.foodordering.model.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    public static final int TWO_COLUMN_TYPE = 1;
    public static final int FULL_WIDTH_COLUMN = 2;
    public static final int DROP_IN_REQUEST = 1412;
    public static String TOKEN = "TOKEN";
    public static String EMAIL = "EMAIL";
    public static boolean isOnline;
    public static final String API_ENDPOINT = "http://10.0.2.2:3000/";
    public static final String API_KEY = "14121998";
    public static Restaurant currentRestaurant;
    public static User currentUser = null;
    public static Set<AddOn> addOnList = new HashSet<>();
    public static List<FavoriteId> currentFavOfRestaurant;

    public static boolean checkFavorite(int id) {
        for (FavoriteId item: currentFavOfRestaurant) {
            Log.d("AAA", item.getFoodId()+"");
            if(item.getFoodId() == id) return true;
        }
        return false;
    }

    public static void removeFavorite(int id) {
        for (FavoriteId item: currentFavOfRestaurant) {
            Log.d("OOO", item.getFoodId()+"-------");
            if(item.getFoodId() == id) currentFavOfRestaurant.remove(item);
        }
    }
}
