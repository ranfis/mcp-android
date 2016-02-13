package com.mcp.mycareerplan;

import android.content.Context;
import android.util.Log;
import com.greengrowapps.ggarest.GgaRest;
import com.greengrowapps.ggarest.RequestBuilder;

class Credentials{
    public String usuario;
    public String clave;
}

public class Login {
    private static final String LOG_TAG = Login.class.getSimpleName();
    Credentials credentials = new Credentials();

    public Login(Context context, String user, String password) {
        Log.v(LOG_TAG, "Login()");
        credentials.usuario = user;
        credentials.clave = password;
        GgaRest.init(context);
        GgaRest.addDefaulteader("Accept", "application/json");
    }

    public RequestBuilder authenticate() {
        Log.v(LOG_TAG, "authenticate()");
        String loginUrl = new ApiUrlBuilder().build("/logon/login");
        Log.d(LOG_TAG, "authenticate()/loginUrl="+loginUrl);

        Log.d(LOG_TAG, "authenticate()/cred.user="+credentials.usuario);
        Log.d(LOG_TAG, "authenticate()/cred.clave="+credentials.clave);
        Log.d(LOG_TAG, "authenticate()/cred="+credentials);

        return GgaRest.ws().post(loginUrl)
                .addHeader("Content-type","application/json")
                .withBody(credentials);
    }
}
