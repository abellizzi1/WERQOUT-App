package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        EditText workoutNameInput = findViewById(R.id.workoutName_input_add_delete);
        EditText dateInput = findViewById(R.id.date_input_add_delete);
        EditText timeInput = findViewById(R.id.time_input_add_delete);
        TextView errorMessage = findViewById(R.id.errorMessage_label_add_delete);
        Button addButton = findViewById(R.id.add_button_add_delete);
    }

    public boolean isValidDate(String date)
    {
        String[] dateSplit = date.split("/");
        if (dateSplit.length == 3 && TextUtils.isDigitsOnly(dateSplit[0] + dateSplit[1] + dateSplit[2])
            && dateSplit[0].length() == 2 && dateSplit[1].length() == 2 && dateSplit[2].length() == 2)
        {
            int month = Integer.parseInt(dateSplit[0]);
            int day = Integer.parseInt(dateSplit[1]);
            int year = Integer.parseInt(dateSplit[2]);
            return (month >= 1 && month <= 12 && day >= 1 && day <= 31 && year >= 21);
        }
        return false;
    }

    public boolean isValidTime(String time)
    {
        String[] timeSplit = time.split(":");
        if (timeSplit.length == 2 && TextUtils.isDigitsOnly(timeSplit[0] + timeSplit[1])
            && timeSplit[0].length() == 2 && timeSplit[1].length() == 2)
        {
            int hour = Integer.parseInt(timeSplit[0]);
            int minute = Integer.parseInt(timeSplit[1]);
            return (hour >= 0 && hour <= 12 && minute >= 0 && minute <= 59);
        }
        return false;
    }
}