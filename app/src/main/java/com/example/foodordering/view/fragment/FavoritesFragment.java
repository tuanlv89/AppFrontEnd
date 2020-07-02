package com.example.foodordering.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;

public class FavoritesFragment extends Fragment {
    private RecyclerView recycler_favorites;
    public static FavoritesFragment newInstance() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        return favoritesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        recycler_favorites = view.findViewById(R.id.recycler_favorites);

        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/
        loadListFavoriteComics();
        Toolbar toolbar = view.findViewById(R.id.toolbar_favorites);
        ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Favorites");


        return view;
    }

    private void loadListFavoriteComics() {

    }


}
