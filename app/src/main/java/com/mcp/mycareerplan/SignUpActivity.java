package com.mcp.mycareerplan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.PatternMatcher;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    EditText nameText;
    EditText lastnameText;
    EditText ageText;
    EditText emailText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;

    DatePickerDialog datePicker;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        nameText = (EditText) findViewById(R.id.signup_name);
        lastnameText = (EditText) findViewById(R.id.signup_lastname);
        ageText = (EditText) findViewById(R.id.signup_age);
        emailText = (EditText) findViewById(R.id.signup_email);
        passwordText = (EditText) findViewById(R.id.signup_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

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
                Toast.makeText(getApplicationContext(), LOG_TAG + " SIGNUP", Toast.LENGTH_SHORT).show();
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
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String lastname = lastnameText.getText().toString();
        String age = ageText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        User newUser = new User();
        newUser.setNombres(name);
        newUser.setApellidos(lastname);
        newUser.setFechaNacimiento(null);
        newUser.setCorreo(email);
        newUser.setUsuario(email);
        newUser.setClave(password);
        newUser.setIdTipoUsuario(1);
        newUser.setIdEstatus(1);
        new Register(newUser).execute();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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
