package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

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
    private static String firstName;

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
                startActivity(new Intent(v.getContext(), RegisterScreen.class));
            }

        });

        submitButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            //getJsonLoginInfo();
                startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
            }
        });

    }

    public void getJsonLoginInfo()
    {
        ServerRequest userLogin = new ServerRequest();
        userLogin.jsonArrayRequest(new VolleyCallback() {
            //Is there a way that I don't need to include this method?
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        JSONObject user = users.getJSONObject(i);
                        if(user.get("email").toString().equals(email))
                        {

                            emailResponse = user.get("email").toString();
                            passwordResponse = user.get("password").toString();
                            firstName = user.get("userName").toString();
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                login();
            }
        },Const.URL_JSON_GET_REQUEST);
    }

    public void login()
    {
        if(emailResponse.equalsIgnoreCase(email)
                && passwordResponse.equals(password))
        {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
        }
        else
            {
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
    }

    public static String getFirstName() { return firstName; }

}