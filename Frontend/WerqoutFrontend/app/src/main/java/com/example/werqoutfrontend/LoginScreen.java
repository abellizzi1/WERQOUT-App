package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.werqoutfrontend.app.AppController;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginScreen extends AppCompatActivity {
    private TextView emailInput;
    private TextView passwordInput;

    private String emailResponse;
    private String passwordResponse;
    private String email;
    private String password;

    private String TAG = ServerRequest.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button submitButton_login = findViewById(R.id.submit_button_login);
        Button registerButton_login = findViewById(R.id.register_button_login);
        emailInput = findViewById(R.id.email_input_login);
        passwordInput = findViewById(R.id.password_input_login);

        registerButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AthleteHomeScreen.class));
            }

        });

        submitButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            jsonGetRequest();
            }
        });

    }
    public void jsonGetRequest()
    {
        //TODO: Replace email in the url with some sort of identifier
        String url = Const.URL_JSON_GET_REQUEST;
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray athletes) {
                        Log.d(TAG, athletes.toString());
                        try {
                            for(int i = 0; i < athletes.length(); i++)
                            {
                                JSONObject athlete = athletes.getJSONObject(i);
                                if(athlete.get("email").toString().equals(email))
                                {
                                    emailResponse = athlete.get("email").toString();
                                    passwordResponse = athlete.get("password").toString();
                                    break;
                                }
                            }
                            login();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        })
        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArray,
                tag_json_obj_post);
    }

    public void login()
    {
        if(emailResponse.equalsIgnoreCase(email)
                && passwordResponse.equals(password))
        {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
        }
        else
            {
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
    }


}