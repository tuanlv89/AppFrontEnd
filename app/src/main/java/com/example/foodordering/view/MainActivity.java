package com.example.foodordering.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.foodordering.R;
import com.example.foodordering.view.fragment.CategoriesFragment;
import com.example.foodordering.view.fragment.FavoritesFragment;
import com.example.foodordering.view.fragment.HomeFragment;
import com.example.foodordering.view.fragment.SettingsFragment;
import com.example.foodordering.receiver.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private BroadcastReceiver receiver;
    private static LinearLayout noInternetLayout;
    private static FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setFragment(HomeFragment.newInstance());

        //Broadcast
        receiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        setFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.nav_categories:
                        setFragment(CategoriesFragment.newInstance());
                        return true;
                    case R.id.nav_favorites:
                        setFragment(FavoritesFragment.newInstance());
                        return true;
                    case R.id.nav_settings:
                        setFragment(SettingsFragment.newInstance());
                        return true;
                    default: return false;
                }
            }
        });


    }

    public static void notificationNetwork(boolean value) {
        if(value == true) {
            noInternetLayout.setVisibility(View.INVISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void updateUI() {
        /*if (mAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }*/
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        mMainNav = findViewById(R.id.nav_main);
        noInternetLayout = findViewById(R.id.no_internet);
        frameLayout = findViewById(R.id.frame_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.i("EXCEPTION", e.getMessage());
        }
    }
}
