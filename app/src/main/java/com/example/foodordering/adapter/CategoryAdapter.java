package com.example.foodordering.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.Interface.IOnRecyclerViewClickListener;
import com.example.foodordering.R;
import com.example.foodordering.model.menu.Category;
import com.example.foodordering.model.eventbus.FoodListEvent;
import com.example.foodordering.ui.view.ListFood;
import com.example.foodordering.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.imgCategory);
        holder.tvCategory.setText(categoryList.get(position).getName());
        holder.setListener((view, position1) -> {
            Toast.makeText(context, categoryList.get(position1).getName(), Toast.LENGTH_LONG).show();
            EventBus.getDefault().postSticky(new FoodListEvent(true, categoryList.get(position)));
            context.startActivity(new Intent(context, ListFood.class));
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_category)
        ImageView imgCategory;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCLick(v, getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(categoryList.size() == 1) {
            return Utils.TWO_COLUMN_TYPE;
        } else {
            if(categoryList.size() % 2 == 0) {
                return Utils.TWO_COLUMN_TYPE;
            } else {
                return (position > 1 && position == categoryList.size()-1) ? Utils.FULL_WIDTH_COLUMN : Utils.TWO_COLUMN_TYPE;
            }
        }
    }
}
