package com.mcp.mycareerplan;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UniPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_pass);

        ImageView logo = (ImageView) findViewById(R.id.unipass_logo);
        if (getIntent().getExtras().containsKey("logo_UNI") && getIntent().getExtras().containsKey("name_UNI")) {
            logo.setImageResource(getIntent().getExtras().getInt("logo_UNI"));
            setTitle(getIntent().getExtras().getString("name_UNI"));
        }


        Button mUniPassBtn  = (Button) findViewById(R.id.unipassButton);
        mUniPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView mUniPassTV = (TextView) findViewById(R.id.unipassTextView);
        mUniPassTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
