package com.mcp.mycareerplan.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.ciclos.Asignatura;

import java.util.List;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class MateriasProximasCustomAdapter extends BaseAdapter {

    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private List<Asignatura> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Asignatura tempValues = null;
    int i = 0;

    public MateriasProximasCustomAdapter(Activity a, List<Asignatura> d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {

        public TextView nombre;
        public TextView codigo;
        public TextView critica;
        public TextView creditos;
        public TextView prerrequisitos;
        public TextView estado;

    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.materiasproximas_child, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nombre = (TextView) vi.findViewById(R.id.materiasproximas_subject_name);
            holder.codigo = (TextView) vi.findViewById(R.id.materiasproximas_codigo);
            holder.critica = (TextView) vi.findViewById(R.id.materiasproximas_critica);
            holder.creditos = (TextView) vi.findViewById(R.id.materiasproximas_creditos);
            holder.prerrequisitos = (TextView) vi.findViewById(R.id.materiasproximas_prerrequisitos);
            holder.estado = (TextView) vi.findViewById(R.id.materiasproximas_estado);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.nombre.setText("No Data");

        } else {
            /***** Get each Model object from List ********/
            tempValues = null;
            tempValues = data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.nombre.setText(tempValues.getNombreasignatura());
            holder.codigo.setText(String.valueOf(tempValues.getCodigo()));
            holder.creditos.setText(String.valueOf(tempValues.getCreditos()));


            String tempPrerrequisitos = "Ninguno";
            if(tempValues.getPrerrequisitos().size()!=0) {
                tempPrerrequisitos = "";
                for(int i=0; i<tempValues.getPrerrequisitos().size(); i++) {
                    tempPrerrequisitos += tempValues.getPrerrequisitos().get(i).getCodigoAsignaturaPredecesora();
                    if (i!=(tempValues.getPrerrequisitos().size()-1)) {
                        tempPrerrequisitos += ", ";
                    }
                }
            }
            holder.prerrequisitos.setText(tempPrerrequisitos);

            if(tempValues.getEsCritica()) {
                holder.critica.setText(activity.getResources().getString(R.string.ruta_critica));
                holder.critica.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.tooltip_rutacritica), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                holder.critica.setText("");
            }

        }
        return vi;
    }


}

