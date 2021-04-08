package com.example.foodordering.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.foodordering.R;
import com.example.foodordering.ui.view.SplashScreen;
import com.example.foodordering.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_name) TextView tvFullName;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.btn_exit) TextView btnExit;
    SharedPreferences sharedPreferences;
    public static AccountFragment newInstance() {
        AccountFragment accountFragment = new AccountFragment();
        return accountFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Utils.EMAIL, "");
                editor.putString(Utils.TOKEN, "");
                editor.apply();
                Utils.currentUser = null;
                Intent intent = new Intent(getContext(), SplashScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/

        return view;
    }

    private void initView() {
        toolbar.setTitle("Account");
        tvFullName.setText(Utils.currentUser.getName());
        tvEmail.setText(Utils.currentUser.getEmail());
        tvPhone.setText(Utils.currentUser.getUserPhone());
        tvAddress.setText(Utils.currentUser.getAddress());
    }


}
