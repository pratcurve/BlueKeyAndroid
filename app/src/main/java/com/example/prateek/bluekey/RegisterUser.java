package com.example.prateek.bluekey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterUser extends AppCompatActivity {

    private EditText userNameText;
    private EditText emailText;
    private EditText numberText;
    private EditText passwordText;
    private CheckBox checkBox;
    private Button signupButton;
    private TextView loginText;
    private LinearLayout linearLayout;
    private String username;
    private String email;
    private String password;
    private String RMN;
    private String fcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Initialize views
        userNameText = (EditText) findViewById(R.id.userName);
        emailText = (EditText) findViewById(R.id.email);
        numberText = (EditText) findViewById(R.id.RMN);
        passwordText = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkbox_password);
        signupButton = (Button) findViewById(R.id.signup_button);
        loginText = (TextView) findViewById(R.id.login_text);
        linearLayout = (LinearLayout) findViewById(R.id.register_form);


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

        //on clicking signup button
        signupButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = userNameText.getText().toString();
                        email = emailText.getText().toString();
                        password = passwordText.getText().toString();
                        RMN = numberText.getText().toString();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        fcm = prefs.getString("regId", null);
                        Log.e("userInputs", username + email + password + fcm + RMN);
                        if (isValidInputs(email, RMN, password,username)) {
                            sendToServer();
                        } else {
                            String error = "There is some problem. Please try again later.";
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        //login activity starts on clicking login Text
        loginText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterUser.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public boolean isValidInputs(CharSequence email,String RMN, String password, String username) {
        if (email != null && RMN != null && password != null && username != null && fcm != null){
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && Patterns.PHONE.matcher(RMN).matches()) return true;
        }
        return false;
    }

    public void sendToServer() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.e("sendToServer", "sending to server");
        String url = "http://192.168.1.7/auto/user_register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                params.put("RMN", RMN);
                params.put("fcm", fcm);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void storeUserInSharedPrefs() {
        Log.e("sharedPrefs", "Shared Prefs Function");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("loggedIn", true);
        editor.putString("email", email);
        editor.apply();

        //start main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
