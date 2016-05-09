package com.mcp.mycareerplan.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.university.Universidad;
import com.mcp.mycareerplan.models.UniversityList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class SelectionUniversityCustomAdapter extends BaseAdapter {

    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private List<Universidad> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Universidad tempValues = null;
    int i = 0;

    public SelectionUniversityCustomAdapter(Activity a, List<Universidad> d, Resources resLocal) {
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
        public TextView correo;
        public TextView telefono;
        public TextView direccion;
        public ImageView image;

    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.selection_university_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nombre = (TextView) vi.findViewById(R.id.selection_university_nombre);
            holder.descripcion = (TextView) vi.findViewById(R.id.selection_university_desc);
            holder.correo = (TextView) vi.findViewById(R.id.selection_university_email);
            holder.telefono = (TextView) vi.findViewById(R.id.selection_university_phone);
            holder.direccion = (TextView) vi.findViewById(R.id.selection_university_address);
            holder.image = (ImageView) vi.findViewById(R.id.selection_university_image);

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

            holder.nombre.setText(tempValues.getNombre());
            holder.descripcion.setText(tempValues.getDescripcion());
            holder.correo.setText(tempValues.getEmail());
            holder.telefono.setText(tempValues.getTelefono());
            holder.direccion.setText(tempValues.getDireccion());

            String urlImage = "";
            if(tempValues.getIdUniversidad()==1) {
                urlImage = "http://www.unibe.edu.do/sites/default/files/imagen_casona_unibe-resize_0.jpg";
            } else if(tempValues.getIdUniversidad()==2) {
                urlImage = "http://boletin.unapec.edu.do/wp-content/uploads/2014/06/41.jpg";
            } else if(tempValues.getIdUniversidad()==3) {
                urlImage = "http://imagenes.universia.net/gc//net/images/institution/27029/Instituto-Tecnologico-Santo-Domingo2_Carrusel.jpg";
            }


            Picasso
                    .with(activity)
                    .load(urlImage)
                    .fit()
                    .placeholder(R.drawable.cargando)
                    .error(R.drawable.nophoto)
                    .centerCrop()
                    .into(holder.image);
        }
        return vi;
    }


}

