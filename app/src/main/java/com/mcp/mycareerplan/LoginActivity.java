package com.mcp.mycareerplan;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Base64;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mcp.mycareerplan.api.Result;
import com.mcp.mycareerplan.api.accounts.Login;
import com.mcp.mycareerplan.api.MCPWebService;
import com.pushbots.push.Pushbots;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_SIGNUP = 0;
    private static final String PACKAGE_NAME = "com.mcp.mycareerplan";
    public static boolean correctCredentials = false;

    private EditText emailText;
    private EditText passwordText;
    private TextView signupLink;
    private Button loginButton;
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
        Pushbots.sharedInstance().init(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        MCPWebService.config(MCPWebService.API_URL); // TODO: Use real url

        // Use to temporary get Hash key for Debug mode
        showHashKey(getApplicationContext());

        getSupportActionBar().hide();

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupLink = (TextView) findViewById(R.id.link_signup);
        loginButton = (Button) findViewById(R.id.loginButton);
        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

        emailText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        passwordText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));



        // Permission for specific birthday requires for us to ask for a Review of the APP on Facebook
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        fbLoginButton.setReadPermissions(permissionNeeds);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "loginButton:setOnClickListener:onClick()");
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), LOG_TAG+" SIGNUP", Toast.LENGTH_SHORT).show();
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                // startActivityForResult(intent, REQUEST_SIGNUP);
                startActivity(intent);
            }
        });

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // SUCCESS!
                getInformationFromFacebook(loginResult);
                //getProfileFromFacebook(loginResult);
            }

            @Override
            public void onCancel() {
                // Oh! Cancel!
            }

            @Override
            public void onError(FacebookException e) {
                // Dude, we have failed
            }
        });
    }

    /**
     * Method 1.0: Allow to get the information needed from Facebook after login with Facebook is success using a GraphRequest
     *
     * @param loginResult shows the data from a success login
     */
    protected void getInformationFromFacebook(LoginResult loginResult) {
        Log.d(LOG_TAG, "getInformationFromFacebook");
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            // PARAMETERS FOR .getString() with only the PUBLIC_PROFILE and EMAIL permission
                            // email, birthday, gender, name, age_range
                            //TODO: GET ALL DATA NEEDED FROM FACEBOOK
                            Toast.makeText(getApplicationContext(), "Hi, " + object.getString("name"), Toast.LENGTH_SHORT).show();
                            //Log.i(LOG_TAG, "email:"+object.getString("email") + ", birthday:"+object.getString("birthday")+", gender:"+object.getString("gender"));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Method 1.0: Allow to get the Profile from Facebook with data like First name, Middle and Last Name. Also profile picture.
     *
     * @param loginResult shows the data from a success login
     */
    protected void getProfileFromFacebook(LoginResult loginResult) {
        Profile profile = Profile.getCurrentProfile();
        //String firstName = profile.getFirstName();
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
     * Method 1.0: Allow to know HashKey of the application so Facebook can compare it when using the Login from Facebook
     *
     * @param context context from the application used in the request
     */
    public void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
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
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Method 1.0: Show error and enable the button
     */
    public void onLoginFailed() {
        Log.d(LOG_TAG, "onLoginFailed");
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
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
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }


}