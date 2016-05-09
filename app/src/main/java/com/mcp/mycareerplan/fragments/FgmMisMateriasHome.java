package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mcp.mycareerplan.App;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.adapters.ExpandableMiPlanAdapter;
import com.mcp.mycareerplan.adapters.ExpandableMisMateriasAdapter;
import com.mcp.mycareerplan.api.ciclos.Asignatura;
import com.mcp.mycareerplan.api.ciclos.AsignaturasCritica;
import com.mcp.mycareerplan.api.ciclos.Ciclo;

import org.codehaus.jackson.map.util.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class FgmMisMateriasHome extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Ciclo> listDataHeader;
    HashMap<Ciclo, List<Asignatura>> listDataChild;
    List<Ciclo> listCiclos;

    public FgmMisMateriasHome() {
        // Required empty public constructor
    }

    public static FgmMisMateriasHome newInstance(List<Ciclo> listCiclos) {
        FgmMisMateriasHome fragment = new FgmMisMateriasHome();
        fragment.setListCiclos(listCiclos);
        return fragment;
    }

    private void setListCiclos(List<Ciclo> listCiclos) {
        Collections.sort(listCiclos, new Comparator<Ciclo>() {
            @Override
            public int compare(Ciclo c1, Ciclo c2)
            {

                return  c1.getBloque().compareTo(c2.getBloque());
            }
        });
        this.listCiclos = listCiclos;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis Asignaturas");

        ((DashboardActivity)getActivity()).getNavigationView().setCheckedItem(R.id.nav_materia);

        View view = inflater.inflate(R.layout.fragment_mis_materias_home,
                container, false);

        expListView = (ExpandableListView) view.findViewById(R.id.mismaterias_exlistview);

        // preparing list data
        populateData();

        listAdapter = new ExpandableMisMateriasAdapter(getActivity(), listDataHeader, listDataChild, getActivity());

        expListView.setAdapter(listAdapter);

        // setting list adapter

        // Inflate the layout for this fragment

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MaterialIntroView.Builder(getActivity())
                        .enableDotAnimation(true)
                        .enableIcon(false)
                        .setFocusGravity(FocusGravity.LEFT)
                        .setFocusType(Focus.MINIMUM)
                        .setDelayMillis(500)
                        .enableFadeAnimation(true)
                        .performClick(true)
                        .setInfoText("Puedes desplegar las asignaturas por período al seleccionar el semestre que estes interesado.")
                        .setTarget(expListView.getChildAt(0))
                        .setUsageId("intro_card_mismaterias") //THIS SHOULD BE UNIQUE ID
                        .show();
            }
        }, 2000);
        return view;
    }

    public void populateData() {
        listDataHeader = new ArrayList<Ciclo>();
        listDataChild = new HashMap<Ciclo, List<Asignatura>>();

//        // Adding child data
//        listDataHeader.add("Semester 1");
//        listDataHeader.add("Semester 2");
//        listDataHeader.add("Semester 3");
//
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("Fisica 1");
//        top250.add("Cálcuo Vectorial");
//        top250.add("Español 2");
//        top250.add("Historia Universal");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("Fisica 2");
//        nowShowing.add("Historia Dominicana");
//        nowShowing.add("Ecuaciones Diferenciales");
//        nowShowing.add("Plan de Negocios");
//        nowShowing.add("Leyes y regulaciones");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("Métodos númericos");
//        comingSoon.add("Seguridad de la información");
//        comingSoon.add("Electiva 2");
//        comingSoon.add("Desarrollo de Negocios Electronico");

        for (int i=0; i<listCiclos.size(); i++) {
            listDataHeader.add(listCiclos.get(i));
            listDataChild.put(listDataHeader.get(i), listCiclos.get(i).getAsignaturas());
        }

//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
