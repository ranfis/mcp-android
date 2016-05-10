package com.mcp.mycareerplan.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.SelectionActivity;
import com.mcp.mycareerplan.adapters.SelectionEscuelaCustomAdapter;
import com.mcp.mycareerplan.adapters.SelectionUniversityCustomAdapter;
import com.mcp.mycareerplan.api.selection.SeleccionAsignatura;
import com.mcp.mycareerplan.api.university.Escuela;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class FgmSelectionEscuela extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ListView listEscuela;
    SelectionEscuelaCustomAdapter adapter;
    public SelectionActivity CustomListView = null;
    private List<Escuela> CustomListViewValuesArr = new ArrayList<>();
    private SeleccionAsignatura seleccionAsignatura;



    public FgmSelectionEscuela() {
        // Required empty public constructor
    }

    public static FgmSelectionEscuela newInstance(List<Escuela> listEscuela, SeleccionAsignatura seleccionAsignatura) {
        FgmSelectionEscuela fragment = new FgmSelectionEscuela();
        fragment.setListEscuela(listEscuela);
        fragment.setSeleccionAsignatura(seleccionAsignatura);
        return fragment;
    }

    public void setListEscuela(List<Escuela> listEscuela) {
        this.CustomListViewValuesArr = listEscuela;
    }

    public void setSeleccionAsignatura(SeleccionAsignatura seleccionAsignatura) {
        this.seleccionAsignatura = seleccionAsignatura;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_selection_escuela));
        View view = inflater.inflate(R.layout.fragment_selection_escuela,
                container, false);

        CustomListView = (SelectionActivity) getActivity();

        Resources res = getResources();
        listEscuela = (ListView) view.findViewById(R.id.list_selection_escuela);

        adapter = new SelectionEscuelaCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listEscuela.setAdapter(adapter);

        listEscuela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Escuela tempValues = CustomListViewValuesArr.get(position);
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmSelectionCarreras fgmSelectionCarrera = FgmSelectionCarreras.newInstance(tempValues.getCarreras(), seleccionAsignatura);
                frgTransaction.replace(R.id.selectionHome, fgmSelectionCarrera);
                frgTransaction.addToBackStack("Selection Carrera");
                frgTransaction.commit();
            }
        });

        return view;    }

}
