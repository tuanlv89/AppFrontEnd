package com.example.foodordering.model.brainTree;

public class BrainTreeToken {
    private String clientToken;
    private boolean success;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BrainTreeToken{" +
                "clientToken='" + clientToken + '\'' +
                ", success=" + success +
                '}';
    }
}
