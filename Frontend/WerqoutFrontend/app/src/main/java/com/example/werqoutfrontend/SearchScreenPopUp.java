package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchScreenPopUp extends AppCompatActivity {
    /**
     * A textview that displays the name of the team clicked
     */
    private TextView titleText;
    /**
     * A textview that asks the user whether or not they would like to join the team
     */
    private TextView description1;
    /**
     * The team that the user is joining
     */
    private JSONObject currentTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen_pop_up);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width * .85),(int)(height * .55));
        String id = (String) getIntent().getSerializableExtra("teamID");
        String teamURL = Const.URL_JSON_REQUEST_TEAMS + "/" + id;
        ServerRequest teamRequest = new ServerRequest();
        teamRequest.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                currentTeam = result;
            }

            @Override
            public void onSuccess(JSONArray result) {

            }
        }, teamURL);
        titleText = findViewById(R.id.title_text_ss_popup);
        description1 = findViewById(R.id.description_ss_popup);
        Button join = findViewById(R.id.request_to_join_button);
        Button cancel = findViewById(R.id.cancel_ssPopUp_button);

        String teamName = (String) getIntent().getSerializableExtra("teamName");
        titleText.setText(teamName);
        String description = "Would like to join " + teamName + "?";
        description1.setText(description);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerRequest joinTeam = new ServerRequest();
                String url = Const.URL_JSON_REQUEST_ATHLETES + "/" +
                        Integer.toString(User.currentUser.getId()) + "/teams";
                joinTeam.jsonObjectRequest(url, 1, currentTeam);
                Toast.makeText(getApplicationContext(), "Joined " + teamName,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}