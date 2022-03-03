package com.example.thestockers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SummaryGroupItemAdapter extends RecyclerView.Adapter<SummaryGroupItemAdapter.GroupItemViewHolder> {

    private List<SummaryGroupItem> groupItemList;

    public SummaryGroupItemAdapter(List<SummaryGroupItem> groupItemList) {
        this.groupItemList = groupItemList;
    }

    @NonNull
    @Override
    public SummaryGroupItemAdapter.GroupItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_group, parent, false);

        return new GroupItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryGroupItemAdapter.GroupItemViewHolder holder, int position) {

        SummaryGroupItem groupItem = groupItemList.get(position);

        holder.tvCost.setText(groupItem.getCost());
        holder.tvItem.setText(groupItem.getCost());
    }

    @Override
    public int getItemCount() {
        return groupItemList.size();
    }

    public class GroupItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCost, tvItem;

        public GroupItemViewHolder (@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tvItem);
            tvCost = itemView.findViewById(R.id.tvCost);
        }
    }
}
