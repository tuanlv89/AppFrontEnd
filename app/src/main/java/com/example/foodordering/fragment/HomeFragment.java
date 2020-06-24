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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodordering.R;
import com.example.foodordering.dialog.ProgressLoading;
import com.example.foodordering.utils.Utils;

import java.util.ArrayList;

import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {
    private Slider slider;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recycler_comic;




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
        recycler_comic = view.findViewById(R.id.recycler_comic);
        slider = view.findViewById(R.id.banner_slider);
        //slider.init(new PicassoLoadingService());
        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.primaryDarkColor);
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/
        setOnRefreshListener();
        return view;
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