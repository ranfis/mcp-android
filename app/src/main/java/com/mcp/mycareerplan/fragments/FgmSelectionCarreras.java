package com.mcp.mycareerplan.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.mcp.mycareerplan.adapters.SelectionCarreraCustomAdapter;
import com.mcp.mycareerplan.api.selection.SeleccionAsignatura;
import com.mcp.mycareerplan.api.university.Carrera;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;


public class FgmSelectionCarreras extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListView listCarrera;
    SelectionCarreraCustomAdapter adapter;
    public SelectionActivity CustomListView = null;
    private List<Carrera> CustomListViewValuesArr = new ArrayList<>();
    private SeleccionAsignatura seleccionAsignatura;


    public FgmSelectionCarreras() {
        // Required empty public constructor
    }


    public static FgmSelectionCarreras newInstance(List<Carrera> list, SeleccionAsignatura seleccionAsignatura) {
        FgmSelectionCarreras fragment = new FgmSelectionCarreras();
        fragment.setListCarrera(list);
        fragment.setSeleccionAsignatura(seleccionAsignatura);
        return fragment;
    }

    public void setListCarrera(List<Carrera> list) {
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_selection_carrera));
        View view = inflater.inflate(R.layout.fragment_selection_carrera,
                container, false);

        CustomListView = (SelectionActivity) getActivity();

        Resources res = getResources();
        listCarrera = (ListView) view.findViewById(R.id.list_selection_carrera);

        adapter = new SelectionCarreraCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listCarrera.setAdapter(adapter);

        listCarrera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Carrera tempValues = CustomListViewValuesArr.get(position);
                seleccionAsignatura.setIdCarrera(tempValues.getIdCarrera());
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmSelectionPensum fgmSelectionPensum = FgmSelectionPensum.newInstance(tempValues.getPensums(), seleccionAsignatura);
                frgTransaction.replace(R.id.selectionHome, fgmSelectionPensum);
                frgTransaction.addToBackStack("Selection Pensum");
                frgTransaction.commit();
            }
        });

        return view;
    }
}
