package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.SelectionActivity;
import com.mcp.mycareerplan.adapters.MateriasProximasCustomAdapter;
import com.mcp.mycareerplan.adapters.SelectionAsignaturaCustomAdapter;
import com.mcp.mycareerplan.api.ciclos.Asignatura;
import com.mcp.mycareerplan.api.ciclos.CicloMiPlan;
import com.mcp.mycareerplan.api.selection.AsignaturasEstudiante;
import com.mcp.mycareerplan.api.selection.RegisterSubject;
import com.mcp.mycareerplan.api.selection.SeleccionAsignatura;
import com.mcp.mycareerplan.api.university.PensumAsignatura;

import java.util.ArrayList;
import java.util.List;

public class Fgm_materias_proximas extends Fragment {

    ListView listAsignatura;
    MateriasProximasCustomAdapter adapter;
    public DashboardActivity CustomListView = null;
    private List<Asignatura> CustomListViewValuesArr = new ArrayList<>();
    private List<CicloMiPlan> listaCiclos;

    public Fgm_materias_proximas() {
        // Required empty public constructor
    }

    public static Fgm_materias_proximas newInstance(List<CicloMiPlan> listaCiclos) {
        Fgm_materias_proximas fragment = new Fgm_materias_proximas();
        fragment.setListAsignatura(listaCiclos);
        return fragment;
    }

    public void setListAsignatura(List<CicloMiPlan> list) {
        this.listaCiclos = list;
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
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_materias_proximas));

        View view = inflater.inflate(R.layout.fragment_materias_proximas, container, false);

        CustomListView = (DashboardActivity) getActivity();

        populateData();

        Resources res = getResources();
        listAsignatura = (ListView) view.findViewById(R.id.materiasproximas_listview);

        adapter = new MateriasProximasCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listAsignatura.setAdapter(adapter);

        return view;
    }

    private void populateData() {
        List<Asignatura> tempListChild = new ArrayList<>();
        for(int i=0; i<listaCiclos.size(); i++) {
            for(int e=0; e<listaCiclos.get(i).getBloques().size(); e++) {
                tempListChild.addAll(listaCiclos.get(i).getBloques().get(e).getAsignaturas());
            }
        }

        this.CustomListViewValuesArr = tempListChild;
    }


}
