package com.example.thestockers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    ListDatabase myDB;
    ArrayList<String> item_id, item_name, item_group, item_quantity;
    ItemCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        int groupID = getIntentData();

        recyclerView = findViewById(R.id.list_recyclerView);
        add_button = findViewById(R.id.add_item_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewList.this, ListAddActivity.class);
                intent.putExtra("GROUP_ID", groupID);
                startActivity(intent);
            }
        });

        myDB = new ListDatabase(ViewList.this);

    }

    int getIntentData(){
        if(getIntent().hasExtra("GROUP_ID")){
            return getIntent().getIntExtra("GROUP_ID", 0);
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}