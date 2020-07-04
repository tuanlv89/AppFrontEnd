package com.example.foodordering.model.addOn;

import java.util.List;

public class AddOnModel {
    private boolean success;
    private List<AddOn> result;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<AddOn> getResult() {
        return result;
    }

    public void setResult(List<AddOn> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
