package com.mcp.mycareerplan;

import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mcp.mycareerplan.api.university.Selection;
import com.mcp.mycareerplan.api.university.Universidad;
import com.mcp.mycareerplan.fragments.FgmHomeList;
import com.mcp.mycareerplan.fragments.FgmSelectionHome;

import java.util.List;

public class SelectionActivity extends AppCompatActivity {

    List<Universidad> listaUniversidades;
    FgmSelectionHome fmgSelectionHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Selection selection = new Selection(SelectionActivity.this);
        selection.execute();


    }


    @Override
    public void onBackPressed() {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count <= 1)
                super.onBackPressed();
            else getFragmentManager().popBackStack();

    }
}
