package com.example.thestockers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton scanner_button;
    ImageView emptyImg;
    TextView emptyTV;

    HomeDatabaseHelper myDB;
    ArrayList<String> entry_id, entry_date, product_name, quantity, unit;
    HomeCustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Find recycler view and scanner float button
        emptyImg = view.findViewById(R.id.empty_img);
        emptyTV = view.findViewById(R.id.empty_tv);
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
        entry_date = new ArrayList<>();
        product_name = new ArrayList<>();
        quantity = new ArrayList<>();
        unit = new ArrayList<>();
        // Query the remote db
        // RemoteDBHelper.populateDB(myDB);

        storeDataInArrays();

        customAdapter = new HomeCustomAdapter(this.getActivity(), this.getActivity(), entry_id, entry_date, product_name, quantity, unit);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(customAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        setHasOptionsMenu(true);
        return view;
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            emptyImg.setVisibility(View.VISIBLE);
            emptyTV.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                entry_id.add(cursor.getString(0));
                entry_date.add(cursor.getString(1));
                product_name.add(cursor.getString(2));
                quantity.add(cursor.getString(3));
                unit.add(cursor.getString(4));
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.refresh_btn){
            // Query remote db to refresh when refresh button is pressed.
            RemoteDBHelper.populateDB(myDB);

            //Refresh fragment for result
            ((MainActivity)getActivity()).switchTab(R.id.homeFragment);

            Toast.makeText(getActivity(), "Database Up-to-date", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}