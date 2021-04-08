package com.example.foodordering.model.addOn;

public class AddOn {
    private int ID;
    private String Description, Name;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setExtraPrice(float extraPrice) {
        ExtraPrice = extraPrice;
    }

    @Override
    public String toString() {
        return "AddOn{" +
                "ID=" + ID +
                ", Description='" + Description + '\'' +
                ", ExtraPrice=" + ExtraPrice +
                '}';
    }
}
