package com.mcp.mycareerplan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcp.mycareerplan.App;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;

public class FgmMyProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView photo;
    private TextView name;
    private TextView email;
    private TextView date;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FgmMyProfile() {
        // Required empty public constructor
    }

    public static FgmMyProfile newInstance() {
        FgmMyProfile fragment = new FgmMyProfile();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_my_profile));

        View view = inflater.inflate(R.layout.fragment_my_profile,
                container, false);

        photo = (ImageView) view.findViewById(R.id.profileImage);
        name = (TextView) view.findViewById(R.id.profileName);
        email = (TextView) view.findViewById(R.id.profileEmail);
        date = (TextView) view.findViewById(R.id.profileDate);

        App.updatePhoto(photo, App.currentUser.getUrl(), getActivity());
        name.setText(App.currentUser.getNombre() + " " + App.currentUser.getApellidos());
        email.setText(App.currentUser.getCorreo());
        date.setText(App.currentUser.getFechanacimiento());

        Button logout = (Button) view.findViewById(R.id.profileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.logoutUser();
            }
        });

        return view;
    }

}
