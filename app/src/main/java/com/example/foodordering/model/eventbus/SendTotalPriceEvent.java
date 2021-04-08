package com.example.foodordering.model.eventbus;

public class SendTotalPriceEvent {
    private String totalPrice;

    public SendTotalPriceEvent(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
