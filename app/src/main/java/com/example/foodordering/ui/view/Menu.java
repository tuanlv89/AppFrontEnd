package com.example.foodordering.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.adapter.CategoryAdapter;
import com.example.foodordering.model.eventbus.MenuItemEvent;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.SpacesItemDecoration;
import com.example.foodordering.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Menu extends AppCompatActivity {

    @BindView(R.id.img_restaurant)
    ImageView imgRestaurant;
    @BindView(R.id.recycler_category)
    RecyclerView recyclerCategory;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton btnCart;
    @BindView(R.id.badge) NotificationBadge badge;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    CategoryAdapter categoryAdapter;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // set column for recyclerview
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(categoryAdapter != null) {
                    switch (categoryAdapter.getItemViewType(position)) {
                        case Utils.ONE_COLUMN_TYPE: return 1;
                        case Utils.FULL_WIDTH_COLUMN: return 2;
                        default: return -1;
                    }
                }
                return -1;
            }
        });
        recyclerCategory.setLayoutManager(layoutManager);
        recyclerCategory.addItemDecoration(new SpacesItemDecoration(8));

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //EventBus
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadMenuByRestaurantId(MenuItemEvent event) {
        if(event.isSuccess()) {
            Picasso.get().load(event.getRestaurant().getImage()).into(imgRestaurant);
            toolbar.setTitle(event.getRestaurant().getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Request category by restaurantId
            compositeDisposable.add(
                    myRestaurantAPI.getCategory(Utils.API_KEY, event.getRestaurant().getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(menuModel -> {
                                        Log.d("GET CATEGORY", menuModel.toString()+ " --" +event.getRestaurant().getId());
                                        categoryAdapter = new CategoryAdapter(this, menuModel.getResult());
                                        recyclerCategory.setAdapter(categoryAdapter);
                                    },
                                    throwable -> {
                                        Log.d("GET CATEGORY ERR", throwable.getMessage());
                                        Toast.makeText(this, "GET CATEGORY " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    })
            );
        } else {

        }
        Log.d("AAA", "CO chay den day ko3");
    }
}
