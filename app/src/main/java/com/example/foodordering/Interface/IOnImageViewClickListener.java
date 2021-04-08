package com.example.foodordering.Interface;

import android.view.View;

public interface IOnImageViewClickListener {
    public void onCalculatePriceListener(View view, int position, boolean isDecrease, boolean isDelete);
}
