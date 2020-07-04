package com.example.foodordering.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.ui.fragment.CategoriesFragment;
import com.example.foodordering.ui.fragment.FavoritesFragment;
import com.example.foodordering.ui.fragment.HomeFragment;
import com.example.foodordering.ui.fragment.SettingsFragment;
import com.example.foodordering.receiver.NetworkChangeReceiver;
import com.example.foodordering.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

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
        requestPermission();

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
                        if(Utils.currentUser != null) setFragment(FavoritesFragment.newInstance());
                        else setFragment(SettingsFragment.newInstance());
                        return true;
                    default: return false;
                }
            }
        });

    }

    private void requestPermission() {
        Log.d("AAA", "vao ne");
        Dexter.withContext(MainActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Log.d("AAA", "OK");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "You must accept this permission to use this app!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "DENIED");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        Log.d("AAA", "AAAAAAA");
                    }
                });
        Log.d("AAA", "Co vao");
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
