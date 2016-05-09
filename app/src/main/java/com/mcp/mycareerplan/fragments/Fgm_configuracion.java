package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.ciclos.CicloMiPlan;

import java.util.List;


public class Fgm_configuracion extends Fragment {


    public Fgm_configuracion() {
        // Required empty public constructor
    }


    public static Fgm_configuracion newInstance() {
        Fgm_configuracion fragment = new Fgm_configuracion();
        return fragment;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Configuración");
        ((DashboardActivity) getActivity()).getNavigationView().setCheckedItem(R.id.nav_footer_settings);

        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

}
