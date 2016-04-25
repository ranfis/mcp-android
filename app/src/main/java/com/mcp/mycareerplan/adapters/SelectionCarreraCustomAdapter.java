package com.mcp.mycareerplan.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.university.Carrera;

import java.util.List;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class SelectionCarreraCustomAdapter extends BaseAdapter {

    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private List<Carrera> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Carrera tempValues = null;
    int i = 0;

    public SelectionCarreraCustomAdapter(Activity a, List<Carrera> d, Resources resLocal) {
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
        public TextView descripcion;

    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.selection_career_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nombre = (TextView) vi.findViewById(R.id.carrera_nombre);
            holder.descripcion = (TextView) vi.findViewById(R.id.carrera_desc);

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

            holder.nombre.setText(tempValues.getNombreCarrera());
            holder.descripcion.setText(tempValues.getDescripcionCarrera());

        }
        return vi;
    }


}

