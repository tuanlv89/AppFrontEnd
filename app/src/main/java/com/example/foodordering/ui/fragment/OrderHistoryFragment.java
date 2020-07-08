package com.example.foodordering.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapter.OrderAdapter;
import com.example.foodordering.common.dialog.ProgressLoading;
import com.example.foodordering.retrofit.IMyRestaurantAPI;
import com.example.foodordering.retrofit.RetrofitClient;
import com.example.foodordering.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class OrderHistoryFragment extends Fragment {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_order) RecyclerView recyclerOrder;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    public static OrderHistoryFragment newInstance() {
        OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
        return orderHistoryFragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_history_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        initView();
        getAllOrder();
        return view;
    }

    private void getAllOrder() {
        ProgressLoading.show(getContext());
        compositeDisposable.add(myRestaurantAPI.getAllOrder("Bearer "+Utils.currentUser.getToken(), Utils.currentUser.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            ProgressLoading.dismiss();
                            if(orderModel.isSuccess()) {
                                //if(orderModel.getResult().size()>0) {
                                    Log.d("OK", orderModel.getResult().toString());
                                    OrderAdapter adapter = new OrderAdapter(getContext(), orderModel.getResult());
                                    recyclerOrder.setAdapter(adapter);
                                //}
                            } else {
                                Log.d("ORDER", orderModel.getMessage());
                            }
                        },
                        throwable -> {ProgressLoading.dismiss(); Log.d("ERROR ORDER", throwable.getMessage());}
                )
        );
    }

    private void initView() {
        toolbar.setTitle("Order history");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerOrder.setLayoutManager(layoutManager);
        recyclerOrder.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Utils.API_ENDPOINT).create(IMyRestaurantAPI.class);
    }


}
