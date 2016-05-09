package com.mcp.mycareerplan.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mcp.mycareerplan.App;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.accounts.Userx;
import com.mcp.mycareerplan.api.application.Notificacion;
import com.mcp.mycareerplan.api.application.PostSubjectApplication;
import com.mcp.mycareerplan.api.semesters.Materia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FgmSolicitudAsignatura extends Fragment  {
    private static final String LOG_TAG = FgmSolicitudAsignatura.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PACKAGE_NAME_ACTIVITY_SUBJECTNOTIFICACION = "com.mcp.mycareerplan.SubjectApplication";
    private static final String TOKEN_NOTIFICACION = "unifacilapptoken";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private Button mBtnSubjectApplication;
    private View view;
    private Userx usuario = App.currentUser;
    private Spinner materias;

    public FgmSolicitudAsignatura() {
        // Required empty public constructor
    }

    public static FgmSolicitudAsignatura newInstance() {
        FgmSolicitudAsignatura fragment = new FgmSolicitudAsignatura();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.title_fragment_solicitud_asignatura));

        view = inflater.inflate(R.layout.fragment_solicitud_asignatura,container, false);
        materias = (Spinner) view.findViewById(R.id.spinner_solicitud_materias);

        mBtnSubjectApplication = (Button)view.findViewById(R.id.subject_application_btn);

                mBtnSubjectApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "mBtnSubjectApplication:setOnClickListener:onClick()");
                Materia materiaSeleccionada = (Materia) materias.getItemAtPosition(materias.getSelectedItemPosition());
//                System.out.println(usuario.getNombreCompleto());
//                System.out.println(usuario.getCorreo());
//                System.out.println(usuario.getMatricula());
//                System.out.println(materiaSeleccionada.getCodigo());
                Snackbar.make(view.findViewById(R.id.subject_application_layout), "Tu solicitud ha sido procesada.", Snackbar.LENGTH_SHORT).show();
                Notificacion notificacion = new Notificacion();
                notificacion.setEmail(null);
                notificacion.setMsg(App.currentUser.getNombre() + ", desea hacer un listado de una materia que aún tienes pendiente.");
                notificacion.setParams1(materiaSeleccionada.getNombre());
                notificacion.setParams2(materiaSeleccionada.getCodigo());
                notificacion.setParams3(null);
                notificacion.setNextActivity(PACKAGE_NAME_ACTIVITY_SUBJECTNOTIFICACION);
                notificacion.setToken(TOKEN_NOTIFICACION);
                PostSubjectApplication postSubjectApplication = new PostSubjectApplication(notificacion, getActivity());
                postSubjectApplication.execute();
            }
        });

        EditText nombre = (EditText) view.findViewById(R.id.subject_application_name);
        EditText matricula = (EditText) view.findViewById(R.id.subject_application_id);
        EditText email = (EditText) view.findViewById(R.id.subject_application_email);
        nombre.setText(usuario.getNombreCompleto());
        matricula.setText("13-0000");
//        matricula.setText(usuario.getMatricula());
        email.setText(usuario.getCorreo());

       ArrayList<Materia> materias = new ArrayList<>(Arrays.asList(
                new Materia("CCI-100","INTRODUCCION A LA INGENIERIA")
                ,new Materia("CGC-100","ORIENTACION UNIVERSITARIA")
                ,new Materia("CGC-110","DEPORTE O CULTURA")
                ,new Materia("CGE-100","ESPAÑOL I")
                ,new Materia("CGI-100","IDIOMA")
                ,new Materia("CGM-170","PRECALCULO")
                ,new Materia("CGQ-100","QUIMICA GENERAL I")
                ,new Materia("CGE-101","ESPAÑOL II")
                ,new Materia("CGI-110","IDIOMA")
                ,new Materia("CGM-180","CALCULO DIFERENCIAL")
                ,new Materia("CGS-100","HISTORIA UNIVERSAL")
                ,new Materia("TI2-111","INTRODUCCION A LA COMPUTACION")
                ,new Materia("TI2-112","LOGICA MATEMATICA")
                ,new Materia("CCI-110","INTRODUCCION A LA PROGRAMACION CGM180")
                ,new Materia("CGC-120","CIENCIA AMBIENTAL")
                ,new Materia("CGE-120","SEMINARIO DE ENSAYO ACADÉMICO")
                ,new Materia("CGF-110","FISICA I")
                ,new Materia("CGM-193","CALCULO INTEGRAL")
                ,new Materia("CGS-110","HISTORIA DOMINICANA")
                ,new Materia("ELG-100","ELECTIVA CICLO GENERAL")
                ,new Materia("CCI-120","LENGUAJE DE PROGRAMACION I")
                ,new Materia("CGC-200","METODOLOGIA DE LA INVESTIGACION")
                ,new Materia("CGF-120","FISICA II")
                ,new Materia("CGM-213","CALCULO VECTORIAL Y MATRICIAL")
                ,new Materia("CGS-140","ANALISIS DE LA REALIDAD DOM. ACTUAL")
                ,new Materia("TI2-211","MATEMATICA DISCRETA")
                ,new Materia("CGF-130","FISICA III")
                ,new Materia("CGM-203","PROBABILIDAD Y ESTADISTICA")
                ,new Materia("CGM-240","ECUACIONES DIFERENCIALES")
                ,new Materia("ELG-110","ELECTIVA CICLO GENERAL")
                ,new Materia("TI2-212","LENGUAJES FORMALES Y TEORIA DE AUTOMATAS")
                ,new Materia("TI2-213","ESTRUCTURA DE DATOS")
                ,new Materia("TI2-214","INTRODUCCION ELECTRONICA DIGITAL")
                ,new Materia("CCI-130","INGENIERÍA ECONÓMICA")
                ,new Materia("CGC-400","SEM. DE LIDERAZGO Y ESPIRITU EMPRENDEDOR")
                ,new Materia("CGM-250","INTRODUCCIÓN A LOS METODOS NUMERICOS")
                ,new Materia("TI2-215","ANALISIS Y DISEÑO DE ALGORITMO")
                ,new Materia("TI2-241","LENGUAJE DE PROGRAMACION II")
                ,new Materia("TI2-242","DISEÑO Y ADMINISTRACION BASE DE DATOS")
                ,new Materia("TI2-700","INGLES PROFESIONAL")
                ,new Materia("CCI-140","INVESTIGACION DE OPERACIONES I")
                ,new Materia("CGC-150","ETICA PROFESIONAL")
                ,new Materia("CGS-120","INTRODUCCION A LA SOCIOLOGIA")
                ,new Materia("TI2-311","ARQUITECTURA DEL COMPUTADOR")
                ,new Materia("TI2-331","ADMINISTRACION DE PROYECTOS")
                ,new Materia("TI2-341","ANALISIS Y DISEÑO DE SISTEMAS TI2242")
                ,new Materia("CCI-150","INVESTIGACION DE OPERACIONES II")
                ,new Materia("TI2-312","SISTEMAS OPERATIVOS")
                ,new Materia("TI2-313","CIRCUITOS LOGICOS DIGITALES")
                ,new Materia("TI1-332","CONTABILIDAD I ")
                ,new Materia("TI2-342","INGENIERIA DE SOFTWARE I")
                ,new Materia("TI2-343","DESARROLLO NEGOCIOS ELECTRONICOS")
                ,new Materia("AD7-110","GERENCIA EMPRESARIAL I")
                ,new Materia("CGM-230","INFERENCIA ESTADISTICA")
                ,new Materia("TI2-314","TEORIA DE COMPILADORES")
                ,new Materia("TI2-321","COMUNICACION Y REDES DE DATOS I")
                ,new Materia("TI2-322","INFRAESTRUCTURA TECNOLOGICA")
                ,new Materia("TI2-344","MINERIA DE DATOS")
                ,new Materia("TI2-500","ELECTIVA PROFESIONAL")
                ,new Materia("AD7-200","DERECHO COMERCIAL")
                ,new Materia("AD7-230","ECONOMIA")
                ,new Materia("TI2-411","INTELIGENCIA ARTIFICIAL")
                ,new Materia("TI2-422","COMUNICACION Y REDES DE DATOS II")
                ,new Materia("TI2-442","ARQUITECTURA DE SOFTWARE")
                ,new Materia("TI2-510","ELECTIVA PROFESIONAL")
                ,new Materia("TI2-412","SIMULACION POR COMPUTADORAS")
                ,new Materia("TI2-423","COMUNICACIONES MOVILES")
                ,new Materia("TI2-431","AUDITORIAS DE LAS TIC")
                ,new Materia("TI2-432","LEYES Y REGULACIONES DE LAS TIC")
                ,new Materia("TI2-443","PRACTICA PROFESIONAL INGENIERIA")
                ,new Materia("TI2-520","ELECTIVA PROFESIONAL")
                ,new Materia("TI2-413","PROYECTO DE GRADO TI2431")
                ,new Materia("TI2-433","GESTION ESTRATEGICA DE LAS TICS")
                ,new Materia("TI2-434","PLAN DE NEGOCIOS")
                ,new Materia("TI2-444","INGENIERIA DE SOFTWARE II")
                ,new Materia("TI2-445","SEGURIDAD DE LA INFORMACION")
        ));

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_solicitud_materias);
        MateriaArrayAdapter adapter = new MateriaArrayAdapter(context,android.R.layout.simple_spinner_item,materias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }

}


class MateriaArrayAdapter extends ArrayAdapter<Materia> {

    private List<Materia> items;
    private Context context;

    public MateriaArrayAdapter(Context context, int layoutId, List<Materia> items) {
        super(context, layoutId, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTextSize(18);
//        v.setTypeface(Typeface.DEFAULT_BOLD);
//        v.setTextAppearance(R.attr.textAppearanceLargePopupMenu);

        if (v == null) {
            v = new TextView(context);
        }
        Materia current = items.get(position);
        v.setText(current.getNombre());
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTextSize(18);
//        v.setTypeface(Typeface.DEFAULT_BOLD);
//        v.setTextAppearance(R.attr.textAppearanceLargePopupMenu);
        if (v == null) {
            v = new TextView(context);
        }
        Materia current = items.get(position);
        v.setText(current.getNombre());
        return v;
    }

    @Override
    public Materia getItem(int position) {
        return items.get(position);
    }

}
