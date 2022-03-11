package com.example.thestockers;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.sql.Struct;
import java.util.List;

public class SummaryGroupAdapter extends RecyclerView.Adapter<SummaryGroupAdapter.GroupViewHolder>{

    private List<SummaryGroup> groupList;

    public SummaryGroupAdapter(List<SummaryGroup> groupList) {
        this.groupList = groupList;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGroup, name, category, amount, date, note;
        private RecyclerView rvGroupItem;

        public GroupViewHolder(View itemView) {
            super(itemView);

            tvGroup = itemView.findViewById(R.id.tvGroup);
            rvGroupItem = itemView.findViewById(R.id.rvGroupItem);
//            name = itemView.findViewById(R.id.name);
//            category = itemView.findViewById(R.id.category);
//            date = itemView.findViewById(R.id.date);
//            amount = itemView.findViewById(R.id.amount);
//            note = itemView.findViewById(R.id.note);
        }
    }

    //constructor
    public SummaryGroupAdapter(Cursor c, int rowLayout, Context context) {
//        this.mCursor = c;
//        this.rowLayout = rowLayout;
//        this.mContext = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_group, parent, false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryGroupAdapter.GroupViewHolder holder, int position) {
        SummaryGroup group = groupList.get(position);
        String strGroup = group.getSummaryGroupName();
        List<SummaryGroupItem> groupItemList = group.getSummaryGroupItems();

        holder.tvGroup.setText(strGroup);
        SummaryGroupItemAdapter groupItemAdapter = new SummaryGroupItemAdapter(groupItemList);
        holder.rvGroupItem.setAdapter(groupItemAdapter);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public void swapList(List<SummaryGroup> list) {
        groupList = list;
        notifyDataSetChanged();
    }
}
