package com.mcp.mycareerplan.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;


public class FgmNucleoUni extends Fragment {


    public FgmNucleoUni() {
        // Required empty public constructor
    }

    public static FgmNucleoUni newInstance() {
        FgmNucleoUni fragment = new FgmNucleoUni();

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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Núcleo Universitario");
        ((DashboardActivity)getActivity()).getNavigationView().setCheckedItem(R.id.nav_nucle);

        View view = inflater.inflate(R.layout.fragment_nucleo_uni, container, false);


        final CardView cvNucleoListadoAsignaturas = (CardView) view.findViewById(R.id.cvNucleo);
        cvNucleoListadoAsignaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FgmSolicitudAsignatura frg = FgmSolicitudAsignatura.newInstance();
                getFragmentManager().beginTransaction()
                        .replace(R.id.homeContent, frg)
                        .addToBackStack("Solicitud Asignatura")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MaterialIntroView.Builder(getActivity())
                        .enableDotAnimation(true)
                        .enableIcon(false)
                        .setFocusGravity(FocusGravity.RIGHT)
                        .setFocusType(Focus.MINIMUM)
                        .setDelayMillis(500)
                        .enableFadeAnimation(true)
                        .performClick(false)
                        .setInfoText("Favor de seleccionar una funcionalidad del Núcleo Universitario para continuar a la siguiente ventana.")
                        .setTarget(cvNucleoListadoAsignaturas)
                        .setUsageId("intro_card_nucleo_solicitud") //THIS SHOULD BE UNIQUE ID
                        .show();
            }
        }, 2000);

        return view;


    }

}
