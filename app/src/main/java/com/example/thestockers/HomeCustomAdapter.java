package com.example.thestockers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeCustomAdapter extends RecyclerView.Adapter<HomeCustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList product, quantity, unit;

    HomeCustomAdapter(Context context, ArrayList product, ArrayList quantity, ArrayList unit){
        this.context = context;
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.productTxt.setText(String.valueOf(product.get(position)));
        holder.quantityTxt.setText(String.valueOf(quantity.get(position)));
        holder.unitTxt.setText(String.valueOf(unit.get(position)));
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productTxt, quantityTxt, unitTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTxt = itemView.findViewById(R.id.product_name_txt);
            quantityTxt = itemView.findViewById(R.id.quantity_txt);
            unitTxt = itemView.findViewById(R.id.unit_txt);
        }
    }

}
