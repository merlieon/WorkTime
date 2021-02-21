package com.example.worktime.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktime.R;
import com.example.worktime.models.GraphModel;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.viewmodels.TimeReportViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DiagramFragment extends Fragment {
    BarChart barChart;
    PieChart pieChart;
    TimeReportViewModel timeReportViewModel;
    ArrayList<GraphModel> graphList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);
        barChart = (BarChart) view.findViewById(R.id.diagramBarChart);
        pieChart = (PieChart) view.findViewById(R.id.diagramPieChart);
        graphList = new ArrayList<>();
        timeReportViewModel = ViewModelProviders.of(getActivity()).get(TimeReportViewModel.class);
        getAllTimeReports();
        return view;
    }

    // Getting all timereports data from database
    private void getAllTimeReports() {
        timeReportViewModel.getAllTimeReports().observe(getActivity(), new Observer<List<TimeReportModel>>() {
            @Override
            public void onChanged(List<TimeReportModel> timeReportList) {
                for (TimeReportModel timeReport : timeReportList) {
                    String date = timeReport.getWorkedDate();
                    String[] parts = date.split("-");
                    String month = parts[1];
                    if (Integer.parseInt(month) < 10)
                        month = parts[1].substring(1);
                    else
                        month = parts[1];
                    String year = parts[0];
                    String day = parts[2];
                    graphList.add(new GraphModel(timeReport.getCompanyName(), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), timeReport.getHours()));
                }
                setPieChart();
                setBarChart();

            }
        });
    }

    // Setting the barchart
    private void setBarChart() {
        Log.d(TAG, "setBarChart: " + graphList);
        ArrayList<BarEntry> monthEntries = new ArrayList<>();
        double[] yArr = new double[12];
        for (int i = 1; i < 13; i++) {
            for (GraphModel graph : graphList) {
                if (graph.getMonth() == i) {
                    yArr[i - 1] += graph.getHours();
                }
            }
            monthEntries.add(new BarEntry(i - 1, (float) yArr[i - 1]));
        }

        BarDataSet barDataSet = new BarDataSet(monthEntries, "entries");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barChart.setData(barData);
        barChart.setPinchZoom(false);
        barChart.fitScreen();
        barChart.getDescription().setText("Antal jobbade timmar per månad");
        barChart.getDescription().setTextSize(12);
        barChart.getDescription().setPosition(1360, 856);
        barChart.animateY(2000);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawGridBackground(true);
        String[] months = new String[]{"Jan", "Feb", "Mars", "April", "Maj", "Juni", "juli", "Aug", "Sept", "Okt", "Nov", "Dec"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setLabelCount(monthEntries.size());
    }

    //Setting the PieChart
    private void setPieChart() {
        Boolean itemExist = false;
        ArrayList<PieEntry> cEntries = new ArrayList<>();
        ArrayList<GraphModel> hoursPerCompanyList = new ArrayList<>();
        for (GraphModel graph : graphList) {
            if (hoursPerCompanyList.isEmpty()) {
                hoursPerCompanyList.add(new GraphModel(graph.getCompanyName(), graph.getMonth(), graph.getHours()));
            } else {
                for (GraphModel hours : hoursPerCompanyList) {
                    if (hours.getCompanyName().contains(graph.getCompanyName())) {
                        hours.setHours(hours.getHours() + graph.getHours());
                        itemExist = true;
                        break;
                    } else {
                        itemExist = false;
                    }
                }
                if (!itemExist)
                    hoursPerCompanyList.add(new GraphModel(graph.getCompanyName(), graph.getMonth(), graph.getHours()));
            }
        }

        for (GraphModel hours : hoursPerCompanyList) {
            cEntries.add(new PieEntry((float) hours.getHours(), hours.getCompanyName()));
        }
        PieDataSet pieDataSet = new PieDataSet(cEntries, "PieDataLabel");

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("Månad");
        pieChart.getDescription().setText("Antal timmar per företag");
        pieChart.getDescription().setTextSize(12);
        pieChart.invalidate();
        pieChart.animate();
    }

}
