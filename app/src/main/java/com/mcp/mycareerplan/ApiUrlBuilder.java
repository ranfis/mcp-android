package com.mcp.mycareerplan;


import android.util.Log;

public class ApiUrlBuilder {
    private static final String LOG_TAG = ApiUrlBuilder.class.getSimpleName();

    private String API_URL = "http://apimcp.azurewebsites.net/api";

    public String build(String endpoint){
        Log.v(LOG_TAG, "build()");
        String url = API_URL+endpoint;
        Log.v(LOG_TAG, "build()/url="+url);
        return url;
    }
}
