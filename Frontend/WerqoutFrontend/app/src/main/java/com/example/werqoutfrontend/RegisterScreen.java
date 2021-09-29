package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.werqoutfrontend.network.ServerRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.typeUser_spinner_register);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(RegisterScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);


        Button submitRegisterButton = findViewById(R.id.register_button_register);
        EditText firstNameInput = findViewById(R.id.firstName_input_register);
        EditText lastNameInput = findViewById(R.id.lastName_input_register);
        EditText emailInput = findViewById(R.id.email_input_register);
        EditText passwordInput = findViewById(R.id.password_input_register);

        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", email);
                params.put("lastName", password);

                ServerRequest request = new ServerRequest();
                request.jsonPostRequest(new JSONObject(params));
            }
        });

    }

    public boolean isValidEmail(String email)
    {
        String[] emailSplit = email.split("@");
        if (emailSplit.length == 2 && isAlphaNumeric(emailSplit[0]))
        {
            String[] periodSplit = emailSplit[1].split(".");
            if (periodSplit.length == 2 && isAlphaNumeric(periodSplit[0] + periodSplit[1]))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isValidName(String name)
    {
        return name.matches("[a-zA-Z]+");
    }

    public boolean isAlphaNumeric(String s)
    {
        return s.matches("[a-zA-Z0-9]+");
    }
}