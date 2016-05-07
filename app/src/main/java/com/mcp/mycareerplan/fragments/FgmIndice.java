package com.mcp.mycareerplan.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;

import java.util.ArrayList;

public class FgmIndice extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BarChart chart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FgmIndice() {
        // Required empty public constructor
    }

    public static FgmIndice newInstance() {
        FgmIndice fragment = new FgmIndice();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_indice));
        ((DashboardActivity)getActivity()).getNavigationView().setCheckedItem(R.id.nav_indice);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(3.6f, 0));
        entries.add(new BarEntry(4.0f, 1));
        entries.add(new BarEntry(3.2f, 2));

        BarDataSet dataset = new BarDataSet(entries, "Índice por semestre");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016-2");
        labels.add("2016-3");
        labels.add("2017-1");


        BarChart chart;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indice,
                container, false);

        chart = (BarChart) view.findViewById(R.id.chart);

        dataset.setColor(ColorTemplate.rgb("#007F80"));

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("Valor de índice semestral");

        return view;
    }

}
