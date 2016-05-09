package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.adapters.MateriasActualesCustomAdapter;
import com.mcp.mycareerplan.adapters.MateriasProximasCustomAdapter;
import com.mcp.mycareerplan.api.ciclos.Asignatura;
import com.mcp.mycareerplan.api.ciclos.Asignatura;

import java.util.ArrayList;
import java.util.List;

public class FgmMateriasList extends Fragment {

    ListView listAsignatura;
    MateriasActualesCustomAdapter adapter;
    public DashboardActivity CustomListView = null;
    private List<Asignatura> CustomListViewValuesArr = new ArrayList<>();

    public FgmMateriasList() {
        // Required empty public constructor
    }

    public static FgmMateriasList newInstance(List<Asignatura> listaCiclos) {
        FgmMateriasList fragment = new FgmMateriasList();
        fragment.setListAsignatura(listaCiclos);
        return fragment;
    }

    public void setListAsignatura(List<Asignatura> list) {
        this.CustomListViewValuesArr = list;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_materias_actuales));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_list, container, false);
        CustomListView = (DashboardActivity) getActivity();


        Resources res = getResources();
        listAsignatura = (ListView) view.findViewById(R.id.materiasactuales_listview);

        adapter = new MateriasActualesCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listAsignatura.setAdapter(adapter);


        return view;
    }



}
