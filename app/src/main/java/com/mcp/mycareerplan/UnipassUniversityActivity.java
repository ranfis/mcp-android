package com.mcp.mycareerplan;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mcp.mycareerplan.adapters.UnipassCustomAdapter;
import com.mcp.mycareerplan.models.UniversityList;

import java.util.ArrayList;

public class UnipassUniversityActivity extends AppCompatActivity {

    ListView listUniversities;
    UnipassCustomAdapter adapter;
    public UnipassUniversityActivity CustomListView = null;
    public ArrayList<UniversityList> CustomListViewValuesArr = new ArrayList<UniversityList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unipass_university);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        listUniversities = (ListView) findViewById(R.id.unipass_listview);

        adapter = new UnipassCustomAdapter(CustomListView, CustomListViewValuesArr, res);
        listUniversities.setAdapter(adapter);

        listUniversities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UniversityList tempValues = CustomListViewValuesArr.get(position);
                Intent intent = new Intent(UnipassUniversityActivity.this, UniPassActivity.class);
                intent.putExtra("logo_UNI", tempValues.getImage());
                intent.putExtra("name_UNI", tempValues.getName());
                startActivity(intent);
            }
        });
    }

    public void setListData() {
        UniversityList sched = new UniversityList();
        sched.setName("Universidad Iberoamericana");
        sched.setImage(R.drawable.unibe);
        CustomListViewValuesArr.add(sched);

        UniversityList sched2 = new UniversityList();
        sched2.setName("Universidad APEC");
        sched2.setImage(R.drawable.logo_apec);
        CustomListViewValuesArr.add(sched2);

        UniversityList sched3 = new UniversityList();
        sched3.setName("Universidad PUCMM");
        sched3.setImage(R.drawable.pucm);
        CustomListViewValuesArr.add(sched3);

        UniversityList sched4 = new UniversityList();
        sched4.setName("Universidad INTEC");
        sched4.setImage(R.drawable.emblema_intec);
        CustomListViewValuesArr.add(sched4);

    }

}
