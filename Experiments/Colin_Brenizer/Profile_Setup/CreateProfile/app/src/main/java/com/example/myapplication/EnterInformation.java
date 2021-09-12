package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterInformation extends AppCompatActivity {
    private String name;
    private String age;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_information);
        Button button = findViewById(R.id.button_return_home);
        EditText nameEntry = findViewById(R.id.entry_name);
        EditText ageEntry = findViewById(R.id.entry_age);
        EditText emailEntry = findViewById(R.id.entry_email);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEntry.getText().toString();
                age = ageEntry.getText().toString();
                email = emailEntry.getText().toString();
                if(name.equals("") || age.equals("") || email.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), ProfilePage.class);
                    i.putExtra("name_message", name);
                    i.putExtra("email_message", email);
                    i.putExtra("age_message", age);
                    startActivity(i);
                }
            };
    })
    ;}
}