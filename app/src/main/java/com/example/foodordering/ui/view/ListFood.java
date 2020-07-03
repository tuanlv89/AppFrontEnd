package com.example.foodordering.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.foodordering.R;
import com.example.foodordering.adapter.FoodAdapter;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.model.eventbus.FoodListEvent;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ListFood extends AppCompatActivity {
    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAdapter foodAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_restaurant)
    ImageView imgRestaurant;
    @BindView(R.id.recycler_foods)
    RecyclerView recyclerFoods;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        if(foodAdapter != null) {
            foodAdapter.onStop();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        ButterKnife.bind(this);
        init();
        initView();

    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerFoods.setLayoutManager(layoutManager);
        recyclerFoods.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
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
    public void loadFoodsByCategory(FoodListEvent event) {
        ProgressLoading.show(this);
        if(event.isSuccess()) {
            Picasso.get().load(event.getCategory().getImage()).into(imgRestaurant);
            toolbar.setTitle(event.getCategory().getName());
            compositeDisposable.add(
                    myRestaurantAPI.getFoodByMenuId(Utils.API_KEY, event.getCategory().getID())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(foodModel -> {
                                        if(foodModel.isSuccess()) {
                                            Log.d("AAA", "vafo day" + foodModel.getResult().size());
                                            foodAdapter = new FoodAdapter(this, foodModel.getResult());
                                            recyclerFoods.setAdapter(foodAdapter);
                                        } else {
                                            Log.d("GET FOOD", foodModel.getMessage());
                                        }
                                    },
                                    throwable -> {

                                    })
            );
        } else {

        }
        ProgressLoading.dismiss();
    }

}
