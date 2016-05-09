package com.mcp.mycareerplan.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mcp.mycareerplan.App;
import com.mcp.mycareerplan.DashboardActivity;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.adapters.ExpandableMiPlanAdapter;
import com.mcp.mycareerplan.api.ciclos.Asignatura;
import com.mcp.mycareerplan.api.ciclos.Ciclo;
import com.mcp.mycareerplan.api.ciclos.CicloMiPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class FgmMiPlanHome extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<CicloMiPlan> listDataHeader;
    HashMap<CicloMiPlan, List<Asignatura>> listDataChild;
    private List<CicloMiPlan> listaCiclos;
    private int year;
    private int costTotal = 0;
    private Double yearDecimals;


    public FgmMiPlanHome() {
    }

    public static FgmMiPlanHome newInstance(List<CicloMiPlan> listaCiclos) {
        FgmMiPlanHome fragment = new FgmMiPlanHome();
        fragment.setListaCiclos(listaCiclos);
        return fragment;
    }

    private void setListaCiclos(List<CicloMiPlan> listaCiclos) {
        this.listaCiclos = listaCiclos;
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi Plan");
        ((DashboardActivity) getActivity()).getNavigationView().setCheckedItem(R.id.nav_plan);

        View view = inflater.inflate(R.layout.fragment_mi_plan_home,
                container, false);

        expListView = (ExpandableListView) view.findViewById(R.id.miplan_exlistview);

        // preparing list data
        populateData();

        listAdapter = new ExpandableMiPlanAdapter(getActivity(), listDataHeader, listDataChild, getActivity());

        // setting list adapter
        expListView.setAdapter(listAdapter);

        TextView costo = (TextView) view.findViewById(R.id.cvCostoTotal);
        costo.setText(Html.fromHtml(App.formatterMoney(costTotal)));

        TextView yearAprox = (TextView) view.findViewById(R.id.cvTiempoAproxPlan);
        yearAprox.setText(App.formatterDate(yearDecimals));

        // Inflate the layout for this fragment

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MaterialIntroView.Builder(getActivity())
                        .enableDotAnimation(true)
                        .enableIcon(false)
                        .setFocusGravity(FocusGravity.LEFT)
                        .setFocusType(Focus.MINIMUM)
                        .setDelayMillis(500)
                        .enableFadeAnimation(true)
                        .performClick(true)
                        .setInfoText("Puedes desplegar las asignaturas por período al seleccionar el semestre que corresponda de tu plan.")
                        .setTarget(expListView.getChildAt(0))
                        .setUsageId("intro_card_miplan") //THIS SHOULD BE UNIQUE ID
                        .show();
            }
        }, 2000);
        return view;
    }

    public void populateData() {
        listDataHeader = new ArrayList<CicloMiPlan>();
        listDataChild = new HashMap<CicloMiPlan, List<Asignatura>>();
        int yearStart = 0;
        int yearEnd = 0;
        int monthStart = 0;
        int monthEnd = 0;

        for (int i=0; i<listaCiclos.size(); i++) {
            if (i==0) {
                yearStart = listaCiclos.get(i).getAnioInicio();
                monthStart = listaCiclos.get(i).getMesInicio();
            }
            if(i==listaCiclos.size()-1) {
                yearEnd = listaCiclos.get(i).getAnioFin();
                monthEnd = listaCiclos.get(i).getMesFin();
            }
            List<Asignatura> tempListChild = new ArrayList<>();
            listDataHeader.add(listaCiclos.get(i));
            for(int e=0; e<listaCiclos.get(i).getBloques().size(); e++) {
                    tempListChild.addAll(listaCiclos.get(i).getBloques().get(e).getAsignaturas());
            }
            costTotal += listaCiclos.get(i).getCosto();
            listDataChild.put(listDataHeader.get(i), tempListChild);
        }
        yearDecimals = Double.valueOf((((( yearEnd-yearStart)*12)+monthEnd-monthStart))/12);
        year = yearEnd - yearStart;
    }

    public void mockup() {
//        listDataHeader = new ArrayList<Ciclo>();
//        listDataChild = new HashMap<Ciclo, List<Asignatura>>();

        Ciclo c1 = new Ciclo();
        c1.setBloqueNombre("Sept-Dic: 2017-1");

        Asignatura a1 = new Asignatura();
        a1.setEsCritica(true);
        a1.setCodigo("CCI-150");
        a1.setCreditos(4);
        a1.setEstadoAsignatura(null);
        a1.setNombreasignatura("Investigación de Operaciones II");
        a1.setPrerrequisitos(null);

        Asignatura a2 = new Asignatura();
        a2.setEsCritica(false);
        a2.setCodigo("TI2-312");
        a2.setCreditos(3);
        a2.setEstadoAsignatura(null);
        a2.setNombreasignatura("Sistemas Operativos");
        a2.setPrerrequisitos(null);

        Asignatura a3 = new Asignatura();
        a3.setEsCritica(false);
        a3.setCodigo("TI2-342");
        a3.setCreditos(3);
        a3.setEstadoAsignatura(null);
        a3.setNombreasignatura("Ingeniería de Software I");
        a3.setPrerrequisitos(null);

        Asignatura a4 = new Asignatura();
        a4.setEsCritica(false);
        a4.setCodigo("CGM-230");
        a4.setCreditos(3);
        a4.setEstadoAsignatura(null);
        a4.setNombreasignatura("Inferencia Estadística");
        a4.setPrerrequisitos(null);

        List<Asignatura> listA = new ArrayList<>();
        listA.add(a1);
        listA.add(a2);
        listA.add(a3);
        listA.add(a4);

        c1.setAsignaturas(listA);

        Ciclo c2 = new Ciclo();
        c2.setBloqueNombre("Ene-Abr: 2017-2");

        Asignatura a5 = new Asignatura();
        a5.setEsCritica(false);
        a5.setCodigo("CCI-130");
        a5.setCreditos(4);
        a5.setEstadoAsignatura(null);
        a5.setNombreasignatura("Ingenería Económica");
        a5.setPrerrequisitos(null);

        Asignatura a7 = new Asignatura();
        a7.setEsCritica(false);
        a7.setCodigo("CGC-400");
        a7.setCreditos(3);
        a7.setEstadoAsignatura(null);
        a7.setNombreasignatura("Seminario de Liderazgo y Espiritu Emprendedor");
        a7.setPrerrequisitos(null);

        Asignatura a8 = new Asignatura();
        a8.setEsCritica(false);
        a8.setCodigo("CGM-250");
        a8.setCreditos(3);
        a8.setEstadoAsignatura(null);
        a8.setNombreasignatura("Introducción a los Métodos Númericos");
        a8.setPrerrequisitos(null);

        Asignatura a9 = new Asignatura();
        a9.setEsCritica(false);
        a9.setCodigo("TI2-215");
        a9.setCreditos(3);
        a9.setEstadoAsignatura(null);
        a9.setNombreasignatura("Análisis y Diseño de Algoritmo");
        a9.setPrerrequisitos(null);

        Asignatura a10 = new Asignatura();
        a10.setEsCritica(true);
        a10.setCodigo("TI2-241");
        a10.setCreditos(3);
        a10.setEstadoAsignatura(null);
        a10.setNombreasignatura("Lenguaje de Programación II");
        a10.setPrerrequisitos(null);

        List<Asignatura> listB = new ArrayList<>();
        listB.add(a5);
        listB.add(a7);
        listB.add(a8);
        listB.add(a9);
        listB.add(a10);

        Ciclo c3 = new Ciclo();
        c3.setBloqueNombre("May-Ago: 2017-3");

        Asignatura b1 = new Asignatura();
        b1.setEsCritica(false);
        b1.setCodigo("CCI-130");
        b1.setCreditos(4);
        b1.setEstadoAsignatura(null);
        b1.setNombreasignatura("Análisis y Diseño de Sistemas");
        b1.setPrerrequisitos(null);

        Asignatura b2 = new Asignatura();
        b2.setEsCritica(false);
        b2.setCodigo("CGC-400");
        b2.setCreditos(3);
        b2.setEstadoAsignatura(null);
        b2.setNombreasignatura("Mineria de Datos");
        b2.setPrerrequisitos(null);

        Asignatura b3 = new Asignatura();
        b3.setEsCritica(false);
        b3.setCodigo("CGM-250");
        b3.setCreditos(3);
        b3.setEstadoAsignatura(null);
        b3.setNombreasignatura("Infraestructura Tecnología");
        b3.setPrerrequisitos(null);

        Asignatura b4 = new Asignatura();
        b4.setEsCritica(false);
        b4.setCodigo("TI2-215");
        b4.setCreditos(3);
        b4.setEstadoAsignatura(null);
        b4.setNombreasignatura("Electiva Profesional II");
        b4.setPrerrequisitos(null);

        Asignatura b5 = new Asignatura();
        b5.setEsCritica(true);
        b5.setCodigo("TI2-241");
        b5.setCreditos(3);
        b5.setEstadoAsignatura(null);
        b5.setNombreasignatura("Teoría de Compiladores");
        b5.setPrerrequisitos(null);

        List<Asignatura> listC = new ArrayList<>();
        listC.add(b1);
        listC.add(b2);
        listC.add(b3);
        listC.add(b4);
        listC.add(b5);

        Ciclo c4 = new Ciclo();
        c4.setBloqueNombre("Sept-Dic: 2018-1");

        Asignatura d1 = new Asignatura();
        d1.setEsCritica(false);
        d1.setCodigo("CCI-130");
        d1.setCreditos(4);
        d1.setEstadoAsignatura(null);
        d1.setNombreasignatura("Administración de Proyectos");
        d1.setPrerrequisitos(null);

        Asignatura d2 = new Asignatura();
        d2.setEsCritica(false);
        d2.setCodigo("CGC-400");
        d2.setCreditos(3);
        d2.setEstadoAsignatura(null);
        d2.setNombreasignatura("Circuito Lógico Digitales");
        d2.setPrerrequisitos(null);

        Asignatura d3 = new Asignatura();
        d3.setEsCritica(false);
        d3.setCodigo("CGM-250");
        d3.setCreditos(3);
        d3.setEstadoAsignatura(null);
        d3.setNombreasignatura("Electiva Profesional III");
        d3.setPrerrequisitos(null);

        Asignatura d4 = new Asignatura();
        d4.setEsCritica(true);
        d4.setCodigo("TI2-215");
        d4.setCreditos(3);
        d4.setEstadoAsignatura(null);
        d4.setNombreasignatura("Estructura de Datos");
        d4.setPrerrequisitos(null);

        Asignatura d5 = new Asignatura();
        d5.setEsCritica(false);
        d5.setCodigo("TI2-241");
        d5.setCreditos(3);
        d5.setEstadoAsignatura(null);
        d5.setNombreasignatura("Lenguajes Formales y Teoría");
        d5.setPrerrequisitos(null);

        List<Asignatura> listD = new ArrayList<>();
        listD.add(d1);
        listD.add(d2);
        listD.add(d3);
        listD.add(d4);
        listD.add(d5);

        Ciclo c5 = new Ciclo();
        c5.setBloqueNombre("Ene-Abr: 2018-2");

        Asignatura e1 = new Asignatura();
        e1.setEsCritica(false);
        e1.setCodigo("CCI-130");
        e1.setCreditos(4);
        e1.setEstadoAsignatura(null);
        e1.setNombreasignatura("Ingenería Económica");
        e1.setPrerrequisitos(null);

        Asignatura e2 = new Asignatura();
        e2.setEsCritica(false);
        e2.setCodigo("CGC-400");
        e2.setCreditos(3);
        e2.setEstadoAsignatura(null);
        e2.setNombreasignatura("Seminario de Liderazgo y Espiritu Emprendedor");
        e2.setPrerrequisitos(null);

        Asignatura e3 = new Asignatura();
        e3.setEsCritica(false);
        e3.setCodigo("CGM-250");
        e3.setCreditos(3);
        e3.setEstadoAsignatura(null);
        e3.setNombreasignatura("Introducción a los Métodos Númericos");
        e3.setPrerrequisitos(null);

        Asignatura e4 = new Asignatura();
        e4.setEsCritica(false);
        e4.setCodigo("TI2-215");
        e4.setCreditos(3);
        e4.setEstadoAsignatura(null);
        e4.setNombreasignatura("Análisis y Diseño de Algoritmo");
        e4.setPrerrequisitos(null);

        Asignatura e5 = new Asignatura();
        e5.setEsCritica(true);
        e5.setCodigo("TI2-241");
        e5.setCreditos(3);
        e5.setEstadoAsignatura(null);
        e5.setNombreasignatura("Lenguaje de Programación II");
        e5.setPrerrequisitos(null);

        List<Asignatura> listE = new ArrayList<>();
        listE.add(e1);
        listE.add(e2);
        listE.add(e3);
        listE.add(e4);
        listE.add(e5);

        Ciclo c6 = new Ciclo();
        c6.setBloqueNombre("May-Ago: 2017-3");

        Asignatura f1 = new Asignatura();
        f1.setEsCritica(false);
        f1.setCodigo("CCI-130");
        f1.setCreditos(4);
        f1.setEstadoAsignatura(null);
        f1.setNombreasignatura("Análisis y Diseño de Sistemas");
        f1.setPrerrequisitos(null);

        Asignatura f2 = new Asignatura();
        f2.setEsCritica(false);
        f2.setCodigo("CGC-400");
        f2.setCreditos(3);
        f2.setEstadoAsignatura(null);
        f2.setNombreasignatura("Mineria de Datos");
        f2.setPrerrequisitos(null);

        Asignatura f3 = new Asignatura();
        f3.setEsCritica(false);
        f3.setCodigo("CGM-250");
        f3.setCreditos(3);
        f3.setEstadoAsignatura(null);
        f3.setNombreasignatura("Infraestructura Tecnología");
        f3.setPrerrequisitos(null);

        Asignatura f4 = new Asignatura();
        f4.setEsCritica(false);
        f4.setCodigo("TI2-215");
        f4.setCreditos(3);
        f4.setEstadoAsignatura(null);
        f4.setNombreasignatura("Electiva Profesional II");
        f4.setPrerrequisitos(null);

        Asignatura f5 = new Asignatura();
        f5.setEsCritica(true);
        f5.setCodigo("TI2-241");
        f5.setCreditos(3);
        f5.setEstadoAsignatura(null);
        f5.setNombreasignatura("Teoría de Compiladores");
        f5.setPrerrequisitos(null);

        List<Asignatura> listF = new ArrayList<>();
        listF.add(f1);
        listF.add(f2);
        listF.add(f3);
        listF.add(f4);
        listF.add(f5);
//
//        listDataHeader.add(c1);
//        listDataHeader.add(c2);
//        listDataHeader.add(c3);
//        listDataHeader.add(c4);
//        listDataHeader.add(c5);
//        listDataHeader.add(c6);

        listDataChild.put(listDataHeader.get(0), listA); // Header, Child data
        listDataChild.put(listDataHeader.get(1), listB); // Header, Child data
        listDataChild.put(listDataHeader.get(2), listC); // Header, Child data
        listDataChild.put(listDataHeader.get(3), listD); // Header, Child data
        listDataChild.put(listDataHeader.get(4), listE); // Header, Child data
        listDataChild.put(listDataHeader.get(4), listF); // Header, Child data


//        // Adding child data
//        listDataHeader.add("Semester 1");
//        listDataHeader.add("Semester 2");
//        listDataHeader.add("Semester 3");
//
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("Fisica 1");
//        top250.add("Cálcuo Vectorial");
//        top250.add("Español 2");
//        top250.add("Historia Universal");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("Fisica 2");
//        nowShowing.add("Historia Dominicana");
//        nowShowing.add("Ecuaciones Diferenciales");
//        nowShowing.add("Plan de Negocios");
//        nowShowing.add("Leyes y regulaciones");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("Métodos númericos");
//        comingSoon.add("Seguridad de la información");
//        comingSoon.add("Electiva 2");
//        comingSoon.add("Desarrollo de Negocios Electronico");
//
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

}
