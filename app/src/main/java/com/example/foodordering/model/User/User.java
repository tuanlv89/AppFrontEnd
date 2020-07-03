package com.example.foodordering.model.User;

public class User {
    private String Email;
    private String UserPhone;
    private String Address;
    private String Name;
    private String token;

    public User() {
    }

    public User(String email, String userPhone) {
        this.Email = email;
        this.UserPhone = userPhone;
    }

    public User(String email, String userPhone, String address, String name, String token) {
        this.Email = email;
        this.UserPhone = userPhone;
        this.Address = address;
        this.Name = name;
        this.token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "Email='" + Email + '\'' +
                ", UserPhone='" + UserPhone + '\'' +
                ", Address='" + Address + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
