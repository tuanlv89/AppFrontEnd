package com.example.foodordering.model.eventbus;

import com.example.foodordering.model.menu.Category;

public class FoodListEvent {
    private boolean success;
    private Category category;
    private String message;

    public FoodListEvent(boolean success, Category category) {
        this.success = success;
        this.category = category;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
