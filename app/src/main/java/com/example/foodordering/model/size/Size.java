package com.example.foodordering.model.size;

public class Size {
    private int ID;
    private String Description;
    private float ExtraPrice;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getExtraPrice() {
        return ExtraPrice;
    }

    public void setExtraPrice(float extraPrice) {
        ExtraPrice = extraPrice;
    }
}
