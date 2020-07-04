package com.example.foodordering.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapter.FavoriteAdapter;
import com.example.foodordering.adapter.FoodAdapter;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritesFragment extends Fragment {
    @BindView(R.id.recycler_favorite) RecyclerView recyclerFavorites;
    @BindView(R.id.toolbar) Toolbar toolbar;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    FavoriteAdapter favoriteAdapter;
    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        if(favoriteAdapter != null) {
            favoriteAdapter.onStop();
        }
        super.onDestroy();
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        return favoritesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        initView();
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/
        loadFavorites(getContext());

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Favorites");


        return view;
    }

    private void loadFavorites(Context context) {
        ProgressLoading.show(context);
        Log.e("A", Utils.currentUser.getToken());
        compositeDisposable.add(
                myRestaurantAPI.getFavoriteByUser("Bearer "+Utils.currentUser.getToken(), Utils.API_KEY, Utils.currentUser.getEmail())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                favoriteModel -> {
                                    if (favoriteModel.isSuccess()) {
                                        recyclerFavorites.setHasFixedSize(true);
                                        favoriteAdapter = new FavoriteAdapter(getContext(), favoriteModel.getResult());
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                        recyclerFavorites.setLayoutManager(layoutManager);
                                        recyclerFavorites.setAdapter(favoriteAdapter);
                                    } else {
                                        Toast.makeText(getContext(), favoriteModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> { Log.d("GET ERROR", throwable.getMessage());}
                        )
        );
        ProgressLoading.dismiss();
    }


    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerFavorites.setLayoutManager(layoutManager);
        recyclerFavorites.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }


}
