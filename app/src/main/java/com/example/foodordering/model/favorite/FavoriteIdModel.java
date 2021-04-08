package com.example.foodordering.model.favorite;

import java.util.List;

public class FavoriteIdModel {
    private boolean success;
    private String message;
    private List<FavoriteId> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FavoriteId> getResult() {
        return result;
    }

    public void setResult(List<FavoriteId> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "FavoriteIdModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
