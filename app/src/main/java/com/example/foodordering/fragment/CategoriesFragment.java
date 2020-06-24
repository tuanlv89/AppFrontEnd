package com.example.foodordering.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.dialog.ProgressLoading;
import com.example.foodordering.utils.Utils;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {
    private RecyclerView recycler_action, recycler_adventure, recycler_comedy, recycler_sports;
    private Toolbar toolbar_categories;
    public ScrollView scrollView;
    public static CategoriesFragment newInstance() {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        return categoriesFragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);
        initView(view);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/
        fetchComicData();
        return view;
    }



    private void setAdapter() {

    }

    private void setLayoutManager() {

    }

    private void fetchComicData() {

    }



    private void initView(View view) {
        toolbar_categories = view.findViewById(R.id.toolbar_categories);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar_categories);

        scrollView = view.findViewById(R.id.scroll_view);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_item, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_home:
                //Intent intent = new Intent(getContext(), SearchActivity.class);
                //startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
