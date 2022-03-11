package com.example.thestockers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    RecyclerView recyclerView;
    FloatingActionButton scanner_button;

    HomeDatabaseHelper myDB;
    ArrayList<String> entry_id, product_name, quantity, unit;
    HomeCustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Find recycler view and scanner float button
        recyclerView = view.findViewById(R.id.home_recyclerView);
        scanner_button = view.findViewById(R.id.scan_to_add_button);
        // When scanner button is clicked, start Scanner Activity
        scanner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScannerActivity.class);
                startActivity(intent);
            }
        });
        myDB = new HomeDatabaseHelper(this.getActivity());
        entry_id = new ArrayList<>();
        product_name = new ArrayList<>();
        quantity = new ArrayList<>();
        unit = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new HomeCustomAdapter(this.getActivity(), this.getActivity(), entry_id, product_name, quantity, unit);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(customAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this.getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                entry_id.add(cursor.getString(0));
                product_name.add(cursor.getString(1));
                quantity.add(cursor.getString(2));
                unit.add(cursor.getString(3));
            }
        }
    }

    public class SwipeItem extends ItemTouchHelper.SimpleCallback {
        HomeCustomAdapter adapter;
        public SwipeItem(HomeCustomAdapter itemAdapter) {
            super(0, ItemTouchHelper.RIGHT);
            this.adapter = itemAdapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getBindingAdapterPosition();
            HomeDatabaseHelper db = new HomeDatabaseHelper(HomeFragment.this.getActivity());
            db.deleteOneRow(entry_id.get(pos));
            this.adapter.deleteItem(pos);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
            int itemHeight = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();

            // Set red swipe background
            final ColorDrawable background = new ColorDrawable(Color.parseColor("#FF0000"));
            background.setBounds(0, viewHolder.itemView.getTop(),
                    (int) (viewHolder.itemView.getLeft() + dX), viewHolder.itemView.getBottom());
            background.draw(c);

            // Calculate position of delete icon
            Drawable icon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete);
            int iconHeight = icon.getIntrinsicHeight();
            int iconWidth = icon.getIntrinsicWidth();
            int iconTop = viewHolder.itemView.getTop() + (itemHeight-iconHeight) / 2;
            int iconMargin = (itemHeight - iconHeight) / 2;
            int iconLeft = viewHolder.itemView.getLeft() +  iconMargin;
            int iconRight = viewHolder.itemView.getLeft() + iconMargin + iconWidth;
            int iconBottom = iconTop + iconHeight;

            //Draw delete icon
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(c);

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

}