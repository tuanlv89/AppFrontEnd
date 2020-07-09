package com.example.foodordering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.model.order.Order;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Order> orderList;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orderList = orderList;
        Log.d("AAA", orderList.get(0).toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAddress.setText(orderList.get(position).getOrderAddress());
        holder.tvOrderId.setText("Order #"+orderList.get(position).getOrderId());
        holder.tvPhone.setText(orderList.get(position).getOrderPhone());
        holder.tvDate.setText("Order on: "+ orderList.get(position).getOrderDate());
        holder.tvPrice.setText(orderList.get(position).getTotalPrice()+ " VND");

        if(orderList.get(position).isCOD() || orderList.get(position).getTransactionId().equals("NONE")) {
            holder.tvPayment.setText("Payment: Cash On Deliver");
        } else {
            holder.tvPayment.setText("TransID: " + orderList.get(position).getTransactionId());
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_phone) TextView tvPhone;
        @BindView(R.id.tv_address) TextView tvAddress;
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_payment) TextView tvPayment;

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
