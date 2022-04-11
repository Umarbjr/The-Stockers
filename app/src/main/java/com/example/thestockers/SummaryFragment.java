package com.example.thestockers;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;


public class SummaryFragment extends Fragment {

    AnyChartView chartView;
    String[] type = {"Waste", "Consumption"};
    int[] qty = {20, 35};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary,container,false);
        chartView = view.findViewById(R.id.pie_chart_view);
        setupPieChart();
        return view;
    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < type.length; i++){
            dataEntries.add(new ValueDataEntry(type[i],qty[i]));
        }

        pie.data(dataEntries);
        chartView.setChart(pie);
    }

}