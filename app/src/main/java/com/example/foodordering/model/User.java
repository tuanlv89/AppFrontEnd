package com.example.foodordering.model;

public class User {
    private String email;
    private String userPhone;
    private String Address;
    private String name;

    public User() {
    }

    public User(String email, String userPhone) {
        this.email = email;
        this.userPhone = userPhone;
    }

    public User(String email, String userPhone, String address, String name) {
        this.email = email;
        this.userPhone = userPhone;
        Address = address;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
