package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_info);

        TextView userEmail = findViewById(R.id.user_email);
        TextView userPass = findViewById(R.id.user_password);
        Button loginBackButton = findViewById(R.id.login_back_button);

        userEmail.setText(Login.getEmail());
        userPass.setText(Login.getPassword());

        loginBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }

        });
    }
}