package com.example.foodordering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.model.addOn.AddOn;
import com.example.foodordering.model.eventbus.AddOnEventChange;
import com.example.foodordering.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddOnAdapter extends RecyclerView.Adapter<AddOnAdapter.ViewHolder> {
    Context context;
    List<AddOn> addOnList;
    LayoutInflater inflater;

    public AddOnAdapter(Context context, List<AddOn> addOnList) {
        this.context = context;
        this.addOnList = addOnList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("AAA", addOnList.size() + addOnList.get(0).toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_addon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cbAddOn.setText(addOnList.get(position).getName() + "("+addOnList.get(position).getExtraPrice()+" VND)");
        holder.cbAddOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Utils.addOnList.add(addOnList.get(position));
                    EventBus.getDefault().postSticky(new AddOnEventChange(true, addOnList.get(position)));
                } else {
                    Utils.addOnList.remove(addOnList.get(position));
                    EventBus.getDefault().postSticky(new AddOnEventChange(false, addOnList.get(position)));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addOnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_addon)
        CheckBox cbAddOn;

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
