package com.mcp.mycareerplan.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.mcp.mycareerplan.R;
import com.mcp.mycareerplan.api.ciclos.Asignatura;

import java.util.HashMap;
import java.util.List;

public class ExpandableMisMateriasAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Asignatura>> _listDataChild;
    private Activity _activity;

    public ExpandableMisMateriasAdapter(Context context, List<String> listDataHeader,
                                        HashMap<String, List<Asignatura>> listChildData, Activity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._activity = activity;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Asignatura childObject = (Asignatura) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.mismaterias_block_list_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.mismaterias_subject_name);
        TextView txtListChild2 = (TextView) convertView
                .findViewById(R.id.mismaterias_subject_code);
        TextView txtListChild3 = (TextView) convertView
                .findViewById(R.id.tv_mismaterias_critica);

        if(childObject.getEsCritica()) {
            txtListChild3.setText(_context.getResources().getString(R.string.ruta_critica));
            txtListChild3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(_context, _context.getResources().getString(R.string.tooltip_rutacritica), Toast.LENGTH_SHORT).show();

                    SuperCardToast superCardToast = new SuperCardToast(_activity);
                    superCardToast.setText(_context.getResources().getString(R.string.tooltip_rutacritica));
                    superCardToast.setDuration(SuperToast.Duration.LONG);
                    superCardToast.setSwipeToDismiss(true);
                    superCardToast.show();

                }
            });
        }
        else{
            txtListChild3.setText("");
        }


        txtListChild.setText(childObject.getNombreasignatura());
        txtListChild2.setText(childObject.getCodigo());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.mismaterias_block_list, null);
        }

        CheckedTextView lblListHeader = (CheckedTextView) convertView
                .findViewById(R.id.mismaterias_group_block);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
