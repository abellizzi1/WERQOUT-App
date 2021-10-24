package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

public class CoachHomeScreen extends AppCompatActivity {
    private TextView temperatureView;
    private ImageView imageView;
    private String weatherIconCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeather();
        setContentView(R.layout.coach_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_coach_home);
        welcomeLabel.setText("Hi, " + LoginScreen.getFirstName());

        /* Weather icon and temperature attributes */
        temperatureView = findViewById(R.id.weather_textview_coach_home);
        imageView = findViewById(R.id.weather_icon_coach_home);

        Button profileButton = findViewById(R.id.profile_button_coach_home);
        Button editGroupButton = findViewById(R.id.edit_button_coach_home);

        editGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), EditGroupsScreen.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfileScreen.class));
            }
        });

        LinearLayout linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_coach_home);
        ViewGroup.LayoutParams params;
        TextView liftText;
        TextView groupText;
        TextView dateText;
        TextView timeText;
        for (int i = 0; i < 10; i++)
        {
            groupText = new TextView(this);
            groupText.setText("Group " + (i+1));
            linearScroll.addView(groupText);
            params = groupText.getLayoutParams();
            setTextSettings(params, groupText);

            liftText = new TextView(this);
            liftText.setText("Chest/Triceps Lift");
            linearScroll.addView(liftText);
            params = liftText.getLayoutParams();
            setTextSettings(params, liftText);

            dateText = new TextView(this);
            linearScroll.addView(dateText);
            params = dateText.getLayoutParams();
            setTextSettings(params, dateText);
            dateText.setText("10/21/21");

            timeText = new TextView(this);
            linearScroll.addView(timeText);
            params = timeText.getLayoutParams();
            setTextSettings(params, timeText);
            timeText.setText("10:00 AM\n");
        }

    }

    private void getWeather()
    {
        ServerRequest weather = new ServerRequest();
        weather.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    //This reads the temperature from the response
                    JSONObject main = result.getJSONObject("main");
                    double temp = Double.parseDouble(main.getString("temp"));
                    int intTemp = (int)(((temp - 273.15) * (1.8)) + 32);
                    temperatureView.setText(intTemp + "ºF");
                    weatherIconCode += result.getJSONArray("weather").getJSONObject(0)
                            .getString("icon");

                    //This takes the icon code and gets the image through a image request
                    ServerRequest image = new ServerRequest();
                    String iconUrl = "http://openweathermap.org/img/w/" + weatherIconCode + ".png";
                    image.imageRequest(new VolleyCallbackImage() {
                        @Override
                        public void onSuccess(Bitmap response) {
                            imageView.setImageBitmap(response);
                        }
                    }, iconUrl);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        }, Const.WEATHER_API);
    }

    public static void setTextSettings(ViewGroup.LayoutParams params, TextView text)
    {
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        text.setLayoutParams(params);
        text.setTextSize(25);
        text.setTextColor(Color.parseColor("#FFFFFF"));
    }
}