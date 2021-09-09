package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Button backToHome = findViewById(R.id.button_profile_back);
        TextView nameEntry =findViewById(R.id.profilepage_text_name);
        TextView ageEntry = findViewById(R.id.profilepage_text_age);
        TextView emailEntry = findViewById(R.id.profilepage_text_email);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name_message");
        String age = intent.getStringExtra("age_message");
        String email = intent.getStringExtra("email_message");
        nameEntry.setText("Name: " + name);
        ageEntry.setText("Age: " + age);
        emailEntry.setText("Email: " + email);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}