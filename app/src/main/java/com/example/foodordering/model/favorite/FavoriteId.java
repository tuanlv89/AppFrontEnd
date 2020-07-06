package com.example.foodordering.model.favorite;

public class FavoriteId {
    private int FoodId;

    public FavoriteId(int foodId) {
        FoodId = foodId;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }
}
