package com.mcp.mycareerplan.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mcp.mycareerplan.App;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.SelectionActivity;
import com.mcp.mycareerplan.UniPassActivity;
import com.mcp.mycareerplan.UnipassUniversityActivity;
import com.mcp.mycareerplan.adapters.SelectionUniversityCustomAdapter;
import com.mcp.mycareerplan.adapters.UnipassCustomAdapter;
import com.mcp.mycareerplan.api.selection.SeleccionAsignatura;
import com.mcp.mycareerplan.api.university.Universidad;
import com.mcp.mycareerplan.models.UniversityList;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;


public class FgmSelectionHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SeleccionAsignatura seleccionAsignatura;

    ListView listUniversity;
    SelectionUniversityCustomAdapter adapter;
    public SelectionActivity CustomListView = null;
    public List<Universidad> CustomListViewValuesArr = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FgmSelectionHome() {
        // Required empty public constructor
    }

    public static FgmSelectionHome newInstance(List<Universidad> listUniversidades) {
        FgmSelectionHome fragment = new FgmSelectionHome();
        fragment.setListUniversity(listUniversidades);
        return fragment;
    }

    public void setListUniversity(List<Universidad> list) {
        this.CustomListViewValuesArr = list;
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_selection_universidad));
        View view = inflater.inflate(R.layout.fragment_selection_home,
                container, false);

        CustomListView = (SelectionActivity) getActivity();

        Resources res = getResources();
        listUniversity = (ListView) view.findViewById(R.id.listUniversity);

        adapter = new SelectionUniversityCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listUniversity.setAdapter(adapter);

        listUniversity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Universidad tempValues = CustomListViewValuesArr.get(position);
                seleccionAsignatura = new SeleccionAsignatura();
                seleccionAsignatura.setIdUniversidad(tempValues.getIdUniversidad());
                seleccionAsignatura.setUsuario(App.currentUser.getCorreo());
                seleccionAsignatura.setIdRegistroEstudiante(1);
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmSelectionEscuela fgmSelectionEscuela = FgmSelectionEscuela.newInstance(tempValues.getEscuelas(), seleccionAsignatura);
                frgTransaction.replace(R.id.selectionHome, fgmSelectionEscuela);
                frgTransaction.addToBackStack("Selection Escuela");
                frgTransaction.commit();

            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
