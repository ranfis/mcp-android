package com.mcp.mycareerplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.greengrowapps.ggarest.GgaRest;
import com.greengrowapps.ggarest.Response;
import com.greengrowapps.ggarest.listeners.OnObjResponseListener;
import com.greengrowapps.ggarest.listeners.OnResponseListener;
import com.mcp.mycareerplan.ent.User;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request Config
        GgaRest.init(this);
        GgaRest.addDefaulteader("Accept", "application/json");

        doGet();

    }


    private void doGet() {
        Log.v(LOG_TAG, "doGet()");
        String API_URL = "http://apimcp.azurewebsites.net/api/Accounts/1";

        GgaRest.ws().get(API_URL)
                .onSuccess(User.class, new OnObjResponseListener<User>() {
                    @Override
                    public void onResponse(int code, User object, Response fullResponse) {

                        Log.d(LOG_TAG, "klk");

                    }
                })
                .onOther(new OnResponseListener() {
                    @Override
                    public void onResponse(int code, Response fullResponse, Exception e) {
                        Log.e(LOG_TAG, "Error");
                    }
                })
                .execute();
    }
}
