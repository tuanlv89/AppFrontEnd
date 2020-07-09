package com.example.foodordering.model.brainTree;

public class BrainTreeTransaction {
    private boolean success;
    private Transaction transaction;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "BrainTreeTransaction{" +
                "success=" + success +
                ", transaction=" + transaction.toString() +
                '}';
    }
}
