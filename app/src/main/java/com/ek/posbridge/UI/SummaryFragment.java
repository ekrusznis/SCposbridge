package com.ek.posbridge.UI;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ek.posbridge.API.Webservices.ListOrders;
import com.ek.posbridge.Models.TempArrayModel;
import com.ek.posbridge.UI.Dialogs.DialogActivity_OrdersChart;
import com.ek.posbridge.UI.Dialogs.DialogActivity_SalesChart;
import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

import com.ek.posbridge.R;

public class SummaryFragment extends Fragment {
    private int RC_LOGIN = 100;
    Button testButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_summary, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        testButton =getView().findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListOrders listorders = new ListOrders();
                listorders.execute();


            }
        });


        final GraphView graph =  getView().findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.setTitle("Orders");
        graph.setTitleColor(R.color.colorPrimaryDark);
        graph.addSeries(series);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DialogActivity_OrdersChart.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), graph, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());
            }
        });

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(20);
// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);




        final PieChart pieChart = getView().findViewById(R.id.pie_chart);
        pieChart.setAboutChart("Sales by SKU");
        List<ChartData> data = new ArrayList<>();
        data.add(new ChartData("First", 35, Color.BLUE, Color.parseColor("#51a1ba")));
        data.add(new ChartData("Second", 25, Color.BLACK, Color.parseColor("#ff0000")));
        data.add(new ChartData("Third", 30, Color.DKGRAY, Color.parseColor("#F57F17")));
        data.add(new ChartData("Fourth", 10, Color.GREEN, Color.parseColor("#368849")));
        pieChart.setChartData(data);
        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DialogActivity_SalesChart.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pieChart, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());
            }
        });

    }



}