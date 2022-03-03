package com.example.thestockers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        //here
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

    //here
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
            int position = viewHolder.getAbsoluteAdapterPosition();
            this.adapter.deleteItem(position);
            HomeDatabaseHelper db = new HomeDatabaseHelper(HomeFragment.this.getActivity());
            db.deleteOneRow(entry_id.get(position));
        }
    }

}