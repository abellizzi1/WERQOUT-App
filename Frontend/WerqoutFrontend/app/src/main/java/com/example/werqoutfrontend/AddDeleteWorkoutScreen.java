package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddDeleteWorkoutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_delete_workout_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.ampm_spinner_add_delete);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(AddDeleteWorkoutScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        Button backButton = findViewById(R.id.back_button_add_delete);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GroupInfoScreen.class));
            }
        });
    }
}