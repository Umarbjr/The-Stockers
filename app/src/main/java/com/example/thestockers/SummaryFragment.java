package com.example.thestockers;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SummaryFragment extends Fragment {

    private RecyclerView rvGroup;
    private SummaryGroupAdapter groupAdapter;
    private SummaryTestDbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary,container,false);
        rvGroup = view.findViewById(R.id.rvGrp);
        rvGroup.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelper = new SummaryTestDbHelper(getActivity());

        ContentValues values = new ContentValues();
        values.put(dbHelper.items, "test items");
        values.put(dbHelper.costs, "testcost - 1.23");
        dbHelper.insertDataGroup(values);
        // need to get data from database here with `getGroup` function in dbHelper
        // will use temp data for now.
//        groupAdapter = new SummaryGroupAdapter(databaseHelper.getGroup());

        SummaryGroupItem groupItemList1;
        SummaryGroupItem groupItemList2;
        groupItemList1 = new SummaryGroupItem("peppers", "4.50");
        groupItemList2 = new SummaryGroupItem("onions", "10.50");
        List<SummaryGroupItem> groupItemList = new ArrayList<SummaryGroupItem>();
        groupItemList.add(groupItemList1);
        groupItemList.add(groupItemList2);
        List<SummaryGroup> groupList = new ArrayList<SummaryGroup>();
        groupList.add (new SummaryGroup("TestName", groupItemList));
        groupList.add (new SummaryGroup("Test2", groupItemList));
        groupAdapter = new SummaryGroupAdapter(groupList);
        rvGroup.setAdapter(groupAdapter);



        return inflater.inflate(R.layout.fragment_summary, container, false);

    }

    @Override
    public void onResume() {
        super.onResume();
        // need to get data from database here with `getGroup` function in dbHelper
        // to repopulate with current data
        // will use temp data for now (null).
        List<SummaryGroup> groupList = null;
//        groupAdapter.swapList(databaseHelper.getGroup());
        groupAdapter.swapList(groupList);
    }
}