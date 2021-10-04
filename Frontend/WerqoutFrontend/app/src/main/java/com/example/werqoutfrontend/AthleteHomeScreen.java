package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONObject;

public class AthleteHomeScreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athlete_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_athlete_home);
        welcomeLabel.setText("Hi, " + LoginScreen.getFirstName());

        Button profileButton = findViewById(R.id.profile_button_athlete_home);
        Button messageButton = findViewById(R.id.messages_button_athlete_home);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfileScreen.class));
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerRequest weather = new ServerRequest();
                weather.jsonGetRequest(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                    try {
                        String weather = result.get("weather").toString();
                        Toast.makeText(getApplicationContext(), weather, Toast.LENGTH_SHORT).show();
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                    }
                },Const.WEATHER_API);

            }
        });

//        ServerRequest weather = new ServerRequest();
//        weather.jsonGetRequest(new VolleyCallback() {
//            @Override
//            public void onSuccess(JSONObject result) {
//
//            }
//        },Const.WEATHER_API);

    }
}