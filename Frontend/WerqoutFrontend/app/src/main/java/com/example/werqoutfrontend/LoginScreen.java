package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;

import org.json.JSONObject;


public class LoginScreen extends AppCompatActivity {
    private TextView email;
    private TextView password;

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
                  ServerRequest request = new ServerRequest();
                  request.jsonGetRequest();

//                  if(athlete.getEmail().equalsIgnoreCase(email.getText().toString() )&&
//                          athlete.getPassword() == password.getText().toString())
//                  {
//                      Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
//                  }
//                  else
//                  {
//                      Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
//                  }
            }
        });
    }

}