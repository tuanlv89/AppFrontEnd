package com.example.foodordering.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodordering.R;
import com.example.foodordering.common.Common;
import com.example.foodordering.dialog.ProgressLoading;
import com.example.foodordering.model.RestaurantModel;
import com.example.foodordering.model.eventbus.RestaurantLoadEvent;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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


    private void loadBanner() {

    }
    private void loadComic() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        //slider.init(new PicassoLoadingService());
        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.primaryDarkColor);
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/

        init();
        initView();
        loadRestaurant();


        setOnRefreshListener();
        return view;
    }

    private void loadRestaurant() {
        ProgressLoading.show(getContext());
        compositeDisposable.add(
                myRestaurantAPI.getAllRestaurant(Common.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurantModel -> {
                            //use eventbus to send local event set adapter and silder
                            EventBus.getDefault().post(new RestaurantLoadEvent(true, restaurantModel.getResult()));
                        },
                        throwable -> {
                            EventBus.getDefault().post(new RestaurantLoadEvent(false, throwable.getMessage()));
                })
        );

    }

    private void initView() {
        ButterKnife.bind(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerRestaurant.setLayoutManager(layoutManager);
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    private void setOnRefreshListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });
    }
}