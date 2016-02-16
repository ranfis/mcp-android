package com.mcp.mycareerplan;

import android.content.Context;
import android.util.Log;

class Credentials{
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    private String usuario = "Jeroo";
    private String clave = "123456";
}

public class Login {
    private static final String LOG_TAG = Login.class.getSimpleName();
    Credentials credentials = new Credentials();

//    public Login(Context context, String user, String password) {
//        Log.v(LOG_TAG, "Login()");
//        credentials.setUsuario(user);
//        credentials.setClave( password);
//        GgaRest.init(context);
//        GgaRest.addDefaulteader("Accept", "application/json");
//    }
//
//    public RequestBuilder authenticate() {
//        Log.v(LOG_TAG, "authenticate()");
//        String loginUrl = new ApiUrlBuilder().build("/logon/login");
//        Log.d(LOG_TAG, "authenticate()/loginUrl="+loginUrl);
//
//        Log.d(LOG_TAG, "authenticate()/cred.user="+credentials.getUsuario());
//        Log.d(LOG_TAG, "authenticate()/cred.clave=" + credentials.getClave());
//        Log.d(LOG_TAG, "authenticate()/cred="+credentials);
//
//        return GgaRest.ws().post(loginUrl)
//                .addHeader("Content-type", "application/json")
//                .withBody(credentials);
//    }
}
