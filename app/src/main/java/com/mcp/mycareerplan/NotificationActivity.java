package com.mcp.mycareerplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcp.mycareerplan.api.university.Universidad;
import com.mcp.mycareerplan.models.UniversidadMock;
import com.squareup.picasso.Picasso;

public class NotificationActivity extends AppCompatActivity {

    ImageView ivImageContent;
    TextView tvTitle;
    TextView tvContent;
    ImageView ivImageUniversity;
    TextView tvNameUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ivImageContent = (ImageView) findViewById(R.id.notification_big_image);
        tvTitle = (TextView) findViewById(R.id.notification_title);
        tvContent = (TextView) findViewById(R.id.notification_content);
        ivImageUniversity = (ImageView) findViewById(R.id.notification_university_logo);
        tvNameUniversity = (TextView) findViewById(R.id.notification_university_name);

        String urlContenido = "";
        int universidad = 0;
        String titulo = "";
        String contenido = "";

        if(savedInstanceState == null){
            Bundle subjectIfo = getIntent().getExtras();
            if(subjectIfo == null){
                urlContenido = "urlContenido";
                universidad = 0;
                titulo = "titulo";
                contenido = "contenido";
            }
            else{
                urlContenido = subjectIfo.getString("urlContenido"); // Codigo de la asignatura
                universidad = Integer.parseInt(subjectIfo.getString("universidad")); // Nombre de la asignatura
                titulo = subjectIfo.getString("titulo"); // Nombre de la asignatura
                contenido = subjectIfo.getString("contenido"); // Nombre de la asignatura
            }
        }
        else{
            urlContenido = (String) savedInstanceState.getSerializable("urlContenido");
            universidad = (int) savedInstanceState.getSerializable("universidad");
            titulo = (String) savedInstanceState.getSerializable("titulo");
            contenido = (String) savedInstanceState.getSerializable("contenido");
        }

        tvTitle.setText(titulo);
        tvContent.setText(Html.fromHtml(contenido));
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());

        UniversidadMock u = universitySelection(universidad);
        tvNameUniversity.setText(u.getName());


        Picasso
                .with(this)
                .load(u.getUrlImage())
                .fit()
                .placeholder(R.drawable.cargando)
                .error(R.drawable.nophoto)
                .centerCrop()
                .into(ivImageUniversity);

        Picasso
                .with(this)
                .load(urlContenido)
                .fit()
                .centerCrop()
                .into(ivImageContent);

    }

    private UniversidadMock universitySelection(int idUniversity) {
        UniversidadMock uni = new UniversidadMock();
        if(idUniversity == 1) {
            uni.setName("Universidad Iberoamericana");
            uni.setId(1);
            uni.setUrlImage(R.drawable.unibe);
        } else if(idUniversity==2) {
            uni.setName("Universidad APEC");
            uni.setId(2);
            uni.setUrlImage(R.drawable.logo_apec);
        }
        return uni;
    }
}
