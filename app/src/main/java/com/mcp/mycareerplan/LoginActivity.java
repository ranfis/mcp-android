package com.mcp.mycareerplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mcp.mycareerplan.api.accounts.Login;
import com.pushbots.push.Pushbots;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_SIGNUP = 1;
    private static final String PACKAGE_NAME = "com.mcp.mycareerplan";
    public static boolean correctCredentials = false;

    private EditText emailText;
    private EditText passwordText;
    private TextView signupLink;
    private Button loginButton;
    private Button uniPassButton;
    private SignInButton googleLoginButton;
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

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
        googleLoginButton = (SignInButton) findViewById(R.id.sign_in_button);

        emailText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        passwordText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));



        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
                Intent intent = new Intent(LoginActivity.this, UnipassUniversityActivity.class );
                startActivity(intent);
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "googleLoginButton:setOnClickListener:onClick()");
                googleSignIn();
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


    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_SIGNUP);
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
//                emailText.setText(data.getStringExtra(SignUpActivity.EXTRA_EMAIL_SIGNUP));
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LOG_TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(LOG_TAG,"USER:getDisplayName "+acct.getDisplayName());
            Log.d(LOG_TAG,"USER:getEmail "+acct.getEmail());
            Log.d(LOG_TAG,"USER:getId "+acct.getId());
            Log.d(LOG_TAG,"USER:getIdToken "+acct.getIdToken());
            Log.d(LOG_TAG,"USER:getPhotoUrl"+acct.getPhotoUrl());

            Login userLogin = new Login(acct.getIdToken(), LoginActivity.this);
            userLogin.execute();

//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            Log.d(LOG_TAG,"STATUS:false");
//            updateUI(false);
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


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG,"No like");
    }
}