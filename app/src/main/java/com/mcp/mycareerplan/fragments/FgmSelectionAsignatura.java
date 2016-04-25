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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.SelectionActivity;
import com.mcp.mycareerplan.adapters.SelectionAsignaturaCustomAdapter;
import com.mcp.mycareerplan.adapters.SelectionAsignaturaCustomAdapter;
import com.mcp.mycareerplan.api.university.PensumAsignatura;

import java.util.ArrayList;
import java.util.List;

public class FgmSelectionAsignatura extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listAsignatura;
    SelectionAsignaturaCustomAdapter adapter;
    public SelectionActivity CustomListView = null;
    private List<PensumAsignatura> CustomListViewValuesArr = new ArrayList<>();


    public FgmSelectionAsignatura() {
        // Required empty public constructor
    }

    public static FgmSelectionAsignatura newInstance(List<PensumAsignatura> list) {
        FgmSelectionAsignatura fragment = new FgmSelectionAsignatura();
        fragment.setListAsignatura(list);
        return fragment;
    }

    public void setListAsignatura(List<PensumAsignatura> list) {
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_selection_asig));
        View view = inflater.inflate(R.layout.fragment_selection_asignatura,
                container, false);

        CustomListView = (SelectionActivity) getActivity();

        Resources res = getResources();
        listAsignatura = (ListView) view.findViewById(R.id.list_asignatura_selection);

        adapter = new SelectionAsignaturaCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listAsignatura.setAdapter(adapter);

        listAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PensumAsignatura tempValues = CustomListViewValuesArr.get(position);
                Toast.makeText(getActivity().getApplicationContext(), "Hola: " + tempValues.getNombreasignatura(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
