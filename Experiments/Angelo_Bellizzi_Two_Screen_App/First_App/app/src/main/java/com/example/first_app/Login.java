package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private static String[] email = new String[1];
    private static String[] password = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText emailInput = findViewById(R.id.email_input);
        EditText passInput = findViewById(R.id.password_input);
        Button submitButton = findViewById(R.id.submit_login_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email[0] = emailInput.getText().toString();
                password[0] = passInput.getText().toString();
                startActivity(new Intent(v.getContext(), LoginInfo.class));
            }
        });
    }

    protected static String getEmail() {
        return email[0];
    }

    protected static String getPassword() {
        return password[0];
    }
}