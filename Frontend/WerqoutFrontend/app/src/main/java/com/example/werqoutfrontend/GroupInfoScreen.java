package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupInfoScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info_screen);

        TextView groupNameLabel = findViewById(R.id.group_name_label_group_info);
        Button backButton = findViewById(R.id.back_button_group_info);
        Button addDeleteButton = findViewById(R.id.addDeleteWorkouts_button_group_info);
        groupNameLabel.setText(CoachGroupsScreen.getSelectedGroup());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
        addDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddDeleteWorkoutScreen.class));
            }
        });

        LinearLayout linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_group_info);
        ViewGroup.LayoutParams params;

        TextView membersText = new TextView(this);
        membersText.setText("Members:");
        linearScroll.addView(membersText);
        params = membersText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, membersText);

        for (int i = 0; i < 4; i++)
        {
            membersText = new TextView(this);
            membersText.setText("John Doe");
            linearScroll.addView(membersText);
            params = membersText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, membersText);
            if (i == 3)
            {
                membersText.setText("John Doe\n");
            }
        }

        TextView liftText = new TextView(this);
        TextView dateText;
        TextView timeText;
        liftText.setText("Workouts:");
        linearScroll.addView(liftText);
        params = liftText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, liftText);
        for (int i = 0; i < 10; i++)
        {
            liftText = new TextView(this);
            liftText.setText("Chest/Triceps Lift");
            linearScroll.addView(liftText);
            params = liftText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, liftText);

            dateText = new TextView(this);
            linearScroll.addView(dateText);
            params = dateText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, dateText);
            dateText.setText("10/21/21");

            timeText = new TextView(this);
            linearScroll.addView(timeText);
            params = timeText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, timeText);
            timeText.setText("10:00 AM\n");
        }
    }
}