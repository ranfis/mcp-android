package com.mcp.mycareerplan.fragments;


import android.app.FragmentTransaction;
import android.content.res.Resources;
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
import com.mcp.mycareerplan.adapters.SelectionPensumCustomAdapter;
import com.mcp.mycareerplan.api.selection.SeleccionAsignatura;
import com.mcp.mycareerplan.api.university.Pensum;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class FgmSelectionPensum extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listPensum;
    SelectionPensumCustomAdapter adapter;
    public SelectionActivity CustomListView = null;
    private List<Pensum> CustomListViewValuesArr = new ArrayList<>();
    private SeleccionAsignatura seleccionAsignatura;


    public FgmSelectionPensum() {
        // Required empty public constructor
    }

    public static FgmSelectionPensum newInstance(List<Pensum> list, SeleccionAsignatura seleccionAsignatura) {
        FgmSelectionPensum fragment = new FgmSelectionPensum();
        fragment.setListPensum(list);
        fragment.setSeleccionAsignatura(seleccionAsignatura);
        return fragment;
    }

    public void setListPensum(List<Pensum> list) {
        this.CustomListViewValuesArr = list;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_selection_pensum));
        View view = inflater.inflate(R.layout.fragment_fgm_selection_pensum,
                container, false);

        CustomListView = (SelectionActivity) getActivity();

        Resources res = getResources();
        listPensum = (ListView) view.findViewById(R.id.list_selection_pensum);

        adapter = new SelectionPensumCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listPensum.setAdapter(adapter);

        listPensum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pensum tempValues = CustomListViewValuesArr.get(position);
                seleccionAsignatura.setIdPensum(tempValues.getIdPemsun());
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmSelectionAsignatura fgmSelectionAsignatura = FgmSelectionAsignatura.newInstance(tempValues.getPensumAsignaturas(), seleccionAsignatura);
                frgTransaction.replace(R.id.selectionHome, fgmSelectionAsignatura);
                frgTransaction.addToBackStack("Selection Asignatura");
                frgTransaction.commit();
            }
        });

        return view;
    }
}
