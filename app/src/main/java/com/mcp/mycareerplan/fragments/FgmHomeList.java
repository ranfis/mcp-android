package com.mcp.mycareerplan.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mcp.mycareerplan.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FgmHomeList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FgmHomeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FgmHomeList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FgmHomeList() {
        // Required empty public constructor
    }

    public static FgmHomeList newInstance() {
        FgmHomeList fragment = new FgmHomeList();

        return fragment;
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_activity_dashboard));

        View view = inflater.inflate(R.layout.fragment_home_list,
                container, false);

        CardView cvIndice = (CardView) view.findViewById(R.id.cvIndice);
        cvIndice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmIndice frg = FgmIndice.newInstance();
                frgTransaction.replace(R.id.homeContent, frg);
                frgTransaction.addToBackStack("Índice Académico");
                frgTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                frgTransaction.commit();
            }
        });

        CardView cvMatActu = (CardView) view.findViewById(R.id.cvMatActu);
        cvMatActu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
                FgmMateriasList frg = FgmMateriasList.newInstance();
                frgTransaction.replace(R.id.homeContent, frg);
                frgTransaction.addToBackStack("Materias actuales");
                frgTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                frgTransaction.commit();
            }
        });

        CardView cvMatProx = (CardView) view.findViewById(R.id.cvMatProx);
        cvMatProx.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}
