package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.Athlete;
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
    private Spinner userSpinner;
    private static String firstName;

    private String TAG = ServerRequest.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        userSpinner = (Spinner) findViewById(R.id.typeUser_spinner_login);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(LoginScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

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
            getJsonLoginInfo();
            }
        });

    }

    public void getJsonLoginInfo()
    {
        if (userSpinner.getSelectedItem().toString().equals("Athlete"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES;
        }
        else if (userSpinner.getSelectedItem().toString().equals("Coach"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_COACHES;
        }
        else
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER;
        }

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
                            int id = Integer.valueOf(user.get("id").toString());
                            Athlete athlete = new Athlete(emailResponse,passwordResponse,firstName,id);
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                login();
            }
        },Const.CURRENT_URL);
    }

    public void login()
    {
        if (!userSpinner.getSelectedItem().toString().equals("Select type of user...") && emailResponse != null)
        {
            if(emailResponse.equalsIgnoreCase(email)
                    && passwordResponse.equals(password))
            {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                if (userSpinner.getSelectedItem().toString().equals("Athlete"))
                {
                    startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
                }
                else if (userSpinner.getSelectedItem().toString().equals("Coach"))
                {
                    startActivity(new Intent(getApplicationContext(), CoachHomeScreen.class));
                }
                else {
                        // startActivity Gym owner home screen
                    }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Type of user not selected", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFirstName() { return firstName; }

}