package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.SelectionActivity;
import com.mcp.mycareerplan.adapters.SelectionAsignaturaCustomAdapter;
import com.mcp.mycareerplan.adapters.SelectionAsignaturaCustomAdapter;
import com.mcp.mycareerplan.api.university.Pensum;
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
        Button button = (Button) view.findViewById(R.id.btnSaveAsignatura);

        button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //TODO: here send to API grades
                 Intent intent = new Intent(getActivity(), DashboardActivity.class);
                 startActivity(intent);
             }
        }
        );

        adapter = new SelectionAsignaturaCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listAsignatura.setAdapter(adapter);

        listAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final PensumAsignatura tempValues = CustomListViewValuesArr.get(position);
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.asig_dialog_title)
                        .items(R.array.condiciones_asignaturas)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (which == 0) {
                                    new MaterialDialog.Builder(getActivity())
                                            .title(R.string.asig_nota_dialog_title)
                                            .content(R.string.asig_nota_dialog_content)
                                            .inputType(InputType.TYPE_CLASS_NUMBER)
                                            .input(R.string.asign_hint, R.string.asig_nota_prefill, new MaterialDialog.InputCallback() {
                                                @Override
                                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                                    // Do something
                                                    CustomListViewValuesArr.get(position).setIsDigit("DIGITADA");
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }).show();
                                }
                            }
                        })
                        .show();
            }
        });
        return view;
    }

}
