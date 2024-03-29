package com.mcp.mycareerplan;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcp.mycareerplan.api.ciclos.CallMiPlan;
import com.mcp.mycareerplan.api.ciclos.Ciclos;
import com.mcp.mycareerplan.fragments.FgmHomeList;
import com.mcp.mycareerplan.fragments.FgmIndice;
import com.mcp.mycareerplan.fragments.FgmMiPlanHome;
import com.mcp.mycareerplan.fragments.FgmMisMateriasHome;
import com.mcp.mycareerplan.fragments.FgmMyProfile;
import com.mcp.mycareerplan.fragments.FgmNucleoUni;
import com.mcp.mycareerplan.fragments.FgmSolicitudAsignatura;
import com.mcp.mycareerplan.fragments.Fgm_configuracion;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String TAG = DashboardActivity.class.getSimpleName();

    private FgmHomeList frHomeList;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * FloatingActionButton disable for now
         */
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Navigation Drawer Objects
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeader = navigationView.inflateHeaderView(R.layout.nav_header_dashboard);
        navigationView.setNavigationItemSelectedListener(this);

        ((TextView)navHeader.findViewById(R.id.navName)).setText(App.currentUser.getNombre() + " " + App.currentUser.getApellidos());
        ((TextView)navHeader.findViewById(R.id.navEmail)).setText(App.currentUser.getCorreo());

        ImageView imgView = (ImageView) navHeader.findViewById(R.id.navImgProfile);
        App.updatePhoto(imgView, App.currentUser.getUrl(), DashboardActivity.this);
        FragmentTransaction frgTransaction = getFragmentManager().beginTransaction();
        frHomeList = FgmHomeList.newInstance();
        frgTransaction.addToBackStack("Dashboard");
        frgTransaction.add(R.id.homeContent, frHomeList);
        frgTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count <= 1)
                super.onBackPressed();
            else getFragmentManager().popBackStack();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    public NavigationView getNavigationView() {
        return this.navigationView;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FgmHomeList frg = FgmHomeList.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.homeContent,frg).addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        } else if (id == R.id.nav_indice) {
            FgmIndice frg = FgmIndice.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.homeContent,frg).addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        } else if (id == R.id.nav_materia) {
            Ciclos ciclos = new Ciclos(this);
            ciclos.execute();
        } else if (id == R.id.nav_plan) {
            CallMiPlan cMiPlan = new CallMiPlan(this);
            cMiPlan.execute();
        } else if(id== R.id.nav_nucle) {
            FgmNucleoUni frg = FgmNucleoUni.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.homeContent, frg)
                    .addToBackStack("Nucleo Universitario")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (id == R.id.nav_perfil) {
            FgmMyProfile frg = FgmMyProfile.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.homeContent, frg)
                    .addToBackStack("Mi perfil")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (id == R.id.nav_footer_settings) {
            Fgm_configuracion frg = Fgm_configuracion.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.homeContent, frg)
                    .addToBackStack("Configuración")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else if (id == R.id.nav_logout) {
            App.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
