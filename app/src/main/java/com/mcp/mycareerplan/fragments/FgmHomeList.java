package com.mcp.mycareerplan.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

    private OnFragmentInteractionListener mListener;

    public FgmHomeList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FgmHomeList.
     */
    // TODO: Rename and change types and number of parameters
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

        View view = inflater.inflate(R.layout.fragment_home_list,
                container, false);
        CardView cvIndice = (CardView) view.findViewById(R.id.cvIndice);
        cvIndice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction frgTransaction = getActivity().getFragmentManager().beginTransaction();
//                FrgDetailVehicle frg = FrgDetailVehicle.newInstance(vehicle);
//                frgTransaction.replace(R.id.lytContent, frg);
//                frgTransaction.addToBackStack("Detail Vehicle");
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
                Toast.makeText(getActivity().getApplicationContext(), "Presionado materias actuales", Toast.LENGTH_SHORT).show();

            }
        });

        CardView cvMatProx = (CardView) view.findViewById(R.id.cvMatProx);
        cvMatProx.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Presionado materias proximas", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
