package com.example.foodordering.model.favorite;

public class FavoriteId {
    private int foodId;

    public FavoriteId(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
