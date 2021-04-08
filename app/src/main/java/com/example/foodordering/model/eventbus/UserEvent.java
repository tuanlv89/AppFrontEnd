package com.example.foodordering.model.eventbus;

import com.example.foodordering.model.user.User;

import java.util.List;

public class UserEvent {
    private boolean success;
    private String message;
    private List<User> userList;
    private String token;

    public UserEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public UserEvent(boolean success, String message, List<User> userList) {
        this.success = success;
        this.message = message;
        this.userList = userList;
    }

    public UserEvent(boolean success, List<User> userList, String token) {
        this.success = success;
        this.userList = userList;
        this.token = token;
    }

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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
