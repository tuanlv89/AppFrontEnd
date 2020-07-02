package com.example.foodordering.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodordering.R;
import com.example.foodordering.adapter.RestaurantAdapter;
import com.example.foodordering.adapter.RestaurantSliderAdapter;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.model.Restaurant.Restaurant;
import com.example.foodordering.model.eventbus.RestaurantLoadEvent;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.services.PicassoLoadingService;
import com.example.foodordering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    @BindView(R.id.banner_slider) Slider bannerSlider;
    @BindView(R.id.recycler_restaurant) RecyclerView recyclerRestaurant;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.primaryDarkColor);
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/

        init();
        loadRestaurant();

        setOnRefreshListener();
        return view;
    }

    private void loadRestaurant() {
        ProgressLoading.show(getContext());
        compositeDisposable.add(
                myRestaurantAPI.getAllRestaurant(Utils.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurantModel -> {
                            //use EventBus to send local event set adapter and Slider
                            EventBus.getDefault().post(new RestaurantLoadEvent(true, restaurantModel.getResult()));
                            Log.d("GET", restaurantModel.getResult().get(0).toString());
                            Log.d("GET", restaurantModel.getResult().size()+"");
                        },
                        throwable -> {
                            EventBus.getDefault().post(new RestaurantLoadEvent(false, throwable.getMessage()));
                            Log.d("GET ERROR", throwable.getMessage());
                })
        );
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
        Slider.init(new PicassoLoadingService());
    }

    private void setOnRefreshListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRestaurant();
            }
        });
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadRestaurant();
            }
        });
        ProgressLoading.dismiss();
    }

    //Register EventBus
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    //UnRegister EventBus
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //Listen EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processRestaurantLoadEvent(RestaurantLoadEvent event) {
        if(event.isSuccess()) {
            displayBanner(event.getRestaurantList());
            displayRestaurant(event.getRestaurantList());
        } else {
            Log.d("ERROR", "[RESTAURANT LOAD] " + event.getMessage());
            Toast.makeText(getContext(), "[RESTAURANT LOAD] " + event.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ProgressLoading.dismiss();
    }

    private void displayRestaurant(List<Restaurant> restaurantList) {
        recyclerRestaurant.setHasFixedSize(true);
        RestaurantAdapter adapter = new RestaurantAdapter(getContext(), restaurantList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerRestaurant.setLayoutManager(layoutManager);
        recyclerRestaurant.setAdapter(adapter);
    }

    private void displayBanner(List<Restaurant> restaurantList) {
        bannerSlider.setAdapter(new RestaurantSliderAdapter(restaurantList));
        bannerSlider.setInterval(2000);
    }
}