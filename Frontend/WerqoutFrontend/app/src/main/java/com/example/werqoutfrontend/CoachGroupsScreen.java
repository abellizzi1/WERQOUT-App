package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        EditText newGroupName = findViewById(R.id.groupName_input_groups);
        String newGroup = newGroupName.getText().toString();

        Button createGroupButton = findViewById(R.id.create_button_groups);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("userName", firstName);
//                params.put("lastName", lastName);
//                params.put("email", email);
//                params.put("password", password);
//
//                //Send the post request to the server
//                if (userSpinner.getSelectedItem().toString().equals("Athlete"))
//                {
//                    Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES;
//                }
//                else if (userSpinner.getSelectedItem().toString().equals("Coach"))
//                {
//                    Const.CURRENT_URL = Const.URL_JSON_REQUEST_COACHES;
//                }
//                else
//                {
//                    Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER;
//                }
//                ServerRequest request = new ServerRequest();
//                request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
//                startActivity(new Intent(v.getContext(), LoginScreen.class));
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", newGroup);
                ServerRequest request = new ServerRequest();
                Const.CURRENT_URL = Const.URL_JSON_REQUEST_TEAMS;
                request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
                Const.CURRENT_URL += LoginScreen.getId() + "/teams";
                request.jsonObjectRequest(Const.CURRENT_URL,2, new JSONObject(params));
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
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