package com.mcp.mycareerplan;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubjectApplication extends AppCompatActivity {
    private static final String LOG_TAG = SubjectApplication.class.getSimpleName();
    private Button mBtnSubjectApplication;
    TextView subjectApplicationHeaderName;
    TextView subjectApplicationHeaderCode;
    EditText subjectApplicationName;
    EditText subjectApplicationId;
    EditText subjectApplicationEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_application);

        mBtnSubjectApplication = (Button)findViewById(R.id.subject_application_btn);

        String params1 = "";
        String params2 = "";
        String params3 = "";

        if(savedInstanceState == null){
            Bundle subjectIfo = getIntent().getExtras();
            if(subjectIfo == null){
                params1 = "params1";
                params2 = "params2";
                params3 = "params3";
            }
            else{
                params1 = subjectIfo.getString("params1"); // Codigo de la asignatura
                params2 = subjectIfo.getString("params2"); // Nombre de la asignatura
                if(subjectIfo.containsKey("params3"))
                params3 = subjectIfo.getString("params3");
            }
        }
        else{
            params1 = (String) savedInstanceState.getSerializable("params1");
            params2 = (String) savedInstanceState.getSerializable("params2");
            if(savedInstanceState.containsKey("params3"))
                params3 = (String) savedInstanceState.getSerializable("params3");
        }

        subjectApplicationHeaderName = (TextView) findViewById(R.id.subject_application_nombr);
        subjectApplicationHeaderCode = (TextView) findViewById(R.id.subject_application_code);
        subjectApplicationName = (EditText) findViewById(R.id.subject_application_name);
        subjectApplicationId = (EditText) findViewById(R.id.subject_application_id);
        subjectApplicationEmail = (EditText) findViewById(R.id.subject_application_email);

        subjectApplicationHeaderName.setText(params1);
        subjectApplicationHeaderCode.setText(params2);
        if(App.currentUser != null) {
            subjectApplicationName.setText(App.currentUser.getNombre() +" "+ App.currentUser.getApellidos());
            subjectApplicationId.setText("13-1054");
            subjectApplicationEmail.setText(App.currentUser.getCorreo());
        }else{
//            Intent login = new Intent(this, LoginActivity.class);
//            startActivityForResult(login,1);
        }

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
