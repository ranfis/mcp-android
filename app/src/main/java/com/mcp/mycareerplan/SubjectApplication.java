package com.mcp.mycareerplan;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubjectApplication extends AppCompatActivity {
    private static final String LOG_TAG = SubjectApplication.class.getSimpleName();
    private Button mBtnSubjectApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_application);

        mBtnSubjectApplication = (Button)findViewById(R.id.subject_application_btn);

        String params1 = null;
        String params2 = null;
        String params3 = null;

        if(savedInstanceState == null){
            Bundle subjectIfo = getIntent().getExtras();
            if(subjectIfo == null){
                params1 = "params1";
                params2 = "params2";
                params3 = "params3";
            }
            else{
                params1 = subjectIfo.getString("PARAM_1"); // Codigo de la asignatura
                params2 = subjectIfo.getString("PARAM_2"); // Nombre de la asignatura
                params3 = subjectIfo.getString("PARAM_3");
            }
        }
        else{
            params1 = (String) savedInstanceState.getSerializable("PARAM_1");
            params2 = (String) savedInstanceState.getSerializable("PARAM_2");
            params3 = (String) savedInstanceState.getSerializable("PARAM_3");
        }

        TextView subjectApplicationHeader = (TextView) findViewById(R.id.subject_application_subject_header);
        String header = subjectApplicationHeader.getText().toString();
        subjectApplicationHeader.setText(header+params1+" - "+params2);



        mBtnSubjectApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "mBtnSubjectApplication:setOnClickListener:onClick()");
                Snackbar.make(findViewById(R.id.activity_subject_application), "Tu solicitud ha sido procesada.", Snackbar.LENGTH_SHORT).show();
                // TODO: Call api here
            }
        });


    }
}
