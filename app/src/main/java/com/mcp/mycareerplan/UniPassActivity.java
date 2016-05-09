package com.mcp.mycareerplan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UniPassActivity extends AppCompatActivity {

    Button mUniPassBtn;
    TextView mUniPassTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_pass);

        ImageView logo = (ImageView) findViewById(R.id.unipass_logo);
        if (getIntent().getExtras().containsKey("logo_UNI") && getIntent().getExtras().containsKey("name_UNI")) {
            logo.setImageResource(getIntent().getExtras().getInt("logo_UNI"));
            setTitle(getIntent().getExtras().getString("name_UNI"));
        }


        mUniPassBtn  = (Button) findViewById(R.id.unipassButton);
            mUniPassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        mUniPassTV = (TextView) findViewById(R.id.unipassTextView);
        mUniPassTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sistema para loguearse con datos de la Universidad", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
