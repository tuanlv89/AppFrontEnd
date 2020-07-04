package com.example.foodordering.utils;

import com.example.foodordering.model.addOn.AddOn;
import com.example.foodordering.model.restaurant.Restaurant;
import com.example.foodordering.model.user.User;

import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static final int ONE_COLUMN_TYPE = 1;
    public static final int FULL_WIDTH_COLUMN = 2;
    public static boolean isOnline;
    public static final String API_ENDPOINT = "http://10.0.2.2:3000/";
    public static final String API_KEY = "14121998";
    public static Restaurant currentRestaurant;
    public static User currentUser = null;
    public static Set<AddOn> addOnList = new HashSet<>();
}
