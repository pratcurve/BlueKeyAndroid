package com.example.prateek.bluekey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Response.*;
import static com.example.prateek.bluekey.R.id.password;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private CheckBox checkBox;
    private Button signInButton;
    private Button signUpButton;
    private TextView forgotPassword;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize views
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkbox_show);
        signInButton = (Button) findViewById(R.id.login_button);
        signUpButton = (Button) findViewById(R.id.signup_button);
        forgotPassword = (TextView) findViewById(R.id.forgot_password_text);

        //click listener sign in button
        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email  = emailText.getText().toString();
                        password = passwordText.getText().toString();
                        Log.e("Email", email);
                        if (isValidInput(email, password)) {
                            sendToServer();
                        } else {
                            String error = "Enter valid email";
                            emailText.setError(error);
                        }
                    }
                }
        );

        //checkbox to show password
        checkBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (((CheckBox) v).isChecked()) {
                           passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                       } else {
                           passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                       }
                    }
                }
        );

        //sign up button click starts sign up activity
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegisterUser.class);
                        startActivity(intent);
                    }
                }
        );

    }

    public boolean isValidInput(CharSequence target, String password) {
        if (target != null && password != null)
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) return true;
        return false;
    }

    public void sendToServer() {
        String url = "http://192.168.1.6/auto/user_login.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest
                (POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Log.e("Success", "Success is ultimate");
                                setLoggedInPrefs();
                            } else {
                                String error = jsonObject.getString("error");
                                Log.e("Error", error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error in Response", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void setLoggedInPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", true);
        editor.putString("email", email);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
