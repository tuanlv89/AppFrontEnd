package com.example.foodordering.model.eventbus;

import com.example.foodordering.model.addOn.AddOn;

import java.util.List;

public class AddOnLoadEvent {
    private boolean success;
    private List<AddOn> addOnList;

    public AddOnLoadEvent(boolean success, List<AddOn> addOnList) {
        this.success = success;
        this.addOnList = addOnList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<AddOn> getAddOnList() {
        return addOnList;
    }

    public void setAddOnList(List<AddOn> addOnList) {
        this.addOnList = addOnList;
    }
}
