package com.mcp.mycareerplan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.PatternMatcher;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcp.mycareerplan.api.accounts.Register;
import com.mcp.mycareerplan.api.accounts.User;
import com.pushbots.push.Pushbots;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();
    private static final String TAG_PUSH = "Registrado";
    public static final String EXTRA_EMAIL_SIGNUP = "EXTRAEMAILSIGNUP";

    EditText nameText;
    EditText lastnameText;
    EditText ageText;
    EditText emailText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;

    private User newUser;


    DatePickerDialog datePicker;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        nameText = (EditText) findViewById(R.id.signup_name);
        lastnameText = (EditText) findViewById(R.id.signup_lastname);
        ageText = (EditText) findViewById(R.id.signup_age);
        emailText = (EditText) findViewById(R.id.signup_email);
        passwordText = (EditText) findViewById(R.id.signup_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        nameText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        lastnameText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        ageText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        passwordText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));
        emailText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_text));


        ageText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePicker.show();
                }
                else {
                    datePicker.hide();
                }
            }
        });

        setDateField();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to login screen by finish or intent
                finish();
            }
        });

    }


    public void setDateField() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ageText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void signup() {
        Log.d(LOG_TAG, "signup");
        if (!validate()) {
            return;
        }

        signupButton.setEnabled(false);

        String name = nameText.getText().toString();
        String lastname = lastnameText.getText().toString();
        String age = ageText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        newUser = new User();
        newUser.setNombre(name);
        newUser.setClave(password);
        newUser.setApellidos(lastname);
        newUser.setFechanacimiento(age);
        newUser.setCorreo(email);
        newUser.setUsuario(email);
        newUser.setIdestatus(1);
        Register newRegister = new Register(newUser, SignUpActivity.this);
        newRegister.execute();
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent returnIntent = new Intent(this, LoginActivity.class);
        returnIntent.putExtra(EXTRA_EMAIL_SIGNUP, newUser.getCorreo());
        setResult(Activity.RESULT_OK, returnIntent);
        //Setting the email to TAG notifications
        Pushbots.sharedInstance().register(emailText.getText().toString(), TAG_PUSH);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.info_success_signup), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String lastname = lastnameText.getText().toString();
        String age = ageText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            lastnameText.setError("at least 3 characters");
            valid = false;
        } else {
            lastnameText.setError(null);
        }

        if (age.isEmpty()) {
            ageText.setError("enter a valid date");
            valid = false;
        } else {
            ageText.setError(null);
        }

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
