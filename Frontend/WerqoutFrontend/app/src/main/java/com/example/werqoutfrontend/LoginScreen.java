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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.werqoutfrontend.app.AppController;
import com.example.werqoutfrontend.utils.Const;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
    private TextView email;
    private TextView password;

    private String TAG = LoginScreen.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button submitButton_login = findViewById(R.id.submit_button_login);
        Button registerButton_login = findViewById(R.id.register_button_login);
        email = findViewById(R.id.email_input_login);
        password = findViewById(R.id.password_input_login);

        registerButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), RegisterScreen.class));
            }

        });

        submitButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonLoginPostRequest();
                jsonGetRequest();
            }
        });
    }

    private void jsonLoginPostRequest()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("firstName", email.getText().toString());
        params.put("lastName", password.getText().toString());

        JsonObjectRequest jsonLogin = new JsonObjectRequest(Request.Method.POST,
                Const.URL_JSON_POST_REQUEST_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(jsonLogin,
                tag_json_obj_post);
    }

    private void jsonGetRequest() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_GET_REQUEST, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //TODO: readResponse(response);
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
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj_post);
    }

    private void readResponse(JSONObject response)
    {
        //TODO: Once response is returned, have some sort of logic to move user
        //to home screen.
    }
}