package com.example.foodordering.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.foodordering.utils.Utils;

import static com.example.foodordering.ui.view.MainActivity.notificationNetwork;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if(isOnline(context)) {
                notificationNetwork(true);
                Utils.isOnline = true;
            } else {
                notificationNetwork(false);
                Utils.isOnline = false;
            }
        } catch (Exception e) {
            Log.i("EXCEPTION", e.getMessage());
        }
    }


    private boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (networkInfo!=null && networkInfo.isConnected());
        } catch (Exception e) {
            Log.i("EXCEPTION", e.getMessage());
            return false;
        }
    }
}