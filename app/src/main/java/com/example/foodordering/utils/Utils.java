package com.example.foodordering.utils;

import com.example.foodordering.model.Restaurant.Restaurant;
import com.example.foodordering.model.User.User;

public class Utils {
    public static final int ONE_COLUMN_TYPE = 1;
    public static final int FULL_WIDTH_COLUMN = 2;
    public static boolean isOnline;
    public static final String API_ENDPOINT = "http://192.168.0.105:3000/";
    public static final String API_KEY = "14121998";
    public static Restaurant currentRestaurant;
    public static User currentUser = null;
}
