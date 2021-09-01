package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button buttonToCounter = (Button)findViewById(R.id.button_to_counter);

        buttonToCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Where we write the logic for our button

                startActivity(new Intent(v.getContext(), Counter.class));
            }

        });
    }
}