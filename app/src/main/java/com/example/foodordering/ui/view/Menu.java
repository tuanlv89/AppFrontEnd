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
import com.example.foodordering.database.CartDataSource;
import com.example.foodordering.database.CartDatabase;
import com.example.foodordering.database.LocalCartDataSource;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    CartDataSource cartDataSource;

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
        Log.d("AAA", "Cart nè1");
        countCart();
        loadFavoriteByRestaurant();
    }

    private void loadFavoriteByRestaurant() {
        compositeDisposable.add(
            myRestaurantAPI.getFavoriteByRestaurant("Bearer "+Utils.currentUser.getToken(),
                    Utils.API_KEY, Utils.currentUser.getEmail(), Utils.currentRestaurant.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favoriteIdModel -> {
                                if (favoriteIdModel.isSuccess()) {
                                    if(favoriteIdModel.getResult() != null && favoriteIdModel.getResult().size()>0) {
                                        Utils.currentFavOfRestaurant = favoriteIdModel.getResult();
                                    } else {
                                        Utils.currentFavOfRestaurant = new ArrayList<>();
                                    }
                                } else {
                                    Toast.makeText(this, favoriteIdModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> { Log.d("ERROR FAV", throwable.getMessage()); }
                    )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA", "Cart nè2");
        countCart();
    }

    private void countCart() {
        Log.d("AAA", "Cart nè3" + Utils.currentUser.getUserPhone() + Utils.currentRestaurant.getId());
        cartDataSource.countItemInCart(Utils.currentUser.getUserPhone(), Utils.currentRestaurant.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("AAA", "Cart nè4");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d("AAA", "Cart nè5" + integer);
                        badge.setText(integer+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR CART", e.getMessage());
                    }
                });
        Log.d("AAA", "Cart nè6");
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
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
            Log.d("TOKEN", "Bearer "+Utils.currentUser.getToken() + Utils.currentUser.toString());
            // Request category by restaurantId
            compositeDisposable.add(
                    myRestaurantAPI.getCategory("Bearer "+ Utils.currentUser.getToken(), Utils.API_KEY, event.getRestaurant().getId())
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
