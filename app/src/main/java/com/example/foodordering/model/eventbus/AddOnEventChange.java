package com.example.foodordering.model.eventbus;

import com.example.foodordering.model.addOn.AddOn;

public class AddOnEventChange {
    private boolean isAdd;
    private AddOn addOn;

    public AddOnEventChange(boolean isAdd, AddOn addOn) {
        this.isAdd = isAdd;
        this.addOn = addOn;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public AddOn getAddOn() {
        return addOn;
    }

    public void setAddOn(AddOn addOn) {
        this.addOn = addOn;
    }
}
