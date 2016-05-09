package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcp.mycareerplan.R;

public class Fgm_materias_proximas extends Fragment {

    public Fgm_materias_proximas() {
        // Required empty public constructor
    }

    public static Fgm_materias_proximas newInstance() {
        Fgm_materias_proximas fragment = new Fgm_materias_proximas();
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_materias_proximas));
        return inflater.inflate(R.layout.fragment_materias_proximas, container, false);
    }

}
