package com.mcp.mycareerplan;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mcp.mycareerplan.api.accounts.Login;
import com.pushbots.push.Pushbots;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_SIGNUP = 1;
    private static final String PACKAGE_NAME = "com.mcp.mycareerplan";
    public static boolean correctCredentials = false;

    private EditText emailText;
    private EditText passwordText;
    private TextView signupLink;
    private Button loginButton;
    private Button uniPassButton;
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
        Pushbots.sharedInstance().init(this);
        setContentView(R.layout.activity_login);

        App.setSessionManager(getApplicationContext());
        App.checkLogin();

        getSupportActionBar().hide();

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupLink = (TextView) findViewById(R.id.link_signup);
        loginButton = (Button) findViewById(R.id.loginButton);
        uniPassButton = (Button) findViewById(R.id.uniPassButton);

        emailText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        passwordText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "loginButton:setOnClickListener:onClick()");
                login();
            }
        });

        uniPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "uniPassButton:setOnClickListener:onClick()");
                Intent intent = new Intent(LoginActivity.this, UniPassActivity.class);
                startActivity(intent);
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "signupLink:setOnClickListener:onClick()");
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, LoginActivity.REQUEST_SIGNUP);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        // Used for get information from Facebook Developers
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Used for get information from Facebook Developers
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /**
     * Method 1.0: Allow to work through the logic of the login access
     */
    public void login() {
        Log.d(LOG_TAG, "login");

        if (!validate()) {
            return;
        }

        loginButton.setEnabled(false);


        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        Login userLogin = new Login(email, password, LoginActivity.this);
        userLogin.execute();

    }

    /**
     * Method 0.5 [REVISION NEEDED]: After signup returns to Login and access through this Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                emailText.setText(data.getStringExtra(SignUpActivity.EXTRA_EMAIL_SIGNUP));
            }
        }
    }

    /**
     * Stop the back button to close the activity, just send it back to stack
     */
    @Override
    public void onBackPressed() {
        // disable going back
        moveTaskToBack(true);
    }

    /**
     * Method 1.0: Set the button and finish the activity when login is success
     */
    public void onLoginSuccess() {
        Log.d(LOG_TAG, "onLoginSuccess");
        Pushbots.sharedInstance().setAlias(emailText.getText().toString());
        loginButton.setEnabled(true);

        App.setSessionManager(getApplicationContext());
        App.createLoginSession(App.currentUser);

        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Method 1.0: Show error and enable the button
     */
    public void onLoginFailed() {
        Log.d(LOG_TAG, "onLoginFailed");
        Toast.makeText(getBaseContext(), getResources().getString(R.string.error_login), Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    /**
     * Method 1.0: Allow to validate all fields used in the activity as their respective value
     *
     * @return
     */
    public boolean validate() {
        Log.d(LOG_TAG, "validate");

        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getResources().getString(R.string.error_invalid_mail));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError(getResources().getString(R.string.error_long_password));
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }


}