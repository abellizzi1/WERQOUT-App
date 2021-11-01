package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CoachGroupsScreen extends AppCompatActivity implements View.OnClickListener {

    private static String selectedGroup = "";
    private LinearLayout linearScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_groups_screen);

        Button backButton = findViewById(R.id.back_button_groups);
        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_groups);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachHomeScreen.class));
            }
        });

        Button groupButton;
        ViewGroup.LayoutParams params;

        for (int i = 0; i < 10; i++)
        {
            groupButton = new Button(this);
            groupButton.setText("Group " + (i+1));
            linearScroll.addView(groupButton);
            params = groupButton.getLayoutParams();
            params.height = 300;
            params.width = 1409;
            groupButton.setLayoutParams(params);
            groupButton.setTextSize(30);
            groupButton.setTextColor(Color.parseColor("#000000"));
            groupButton.setBackgroundColor(Color.parseColor("#00FFA7"));
            groupButton.setId(i);
            groupButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        selectedGroup = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        startActivity(new Intent(v.getContext(), GroupInfoScreen.class));
    }

    public static String getSelectedGroup()
    {
        return selectedGroup;
    }
}