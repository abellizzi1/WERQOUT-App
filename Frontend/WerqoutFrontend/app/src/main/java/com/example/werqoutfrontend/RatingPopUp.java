package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingPopUp extends AppCompatActivity {
    /**
     * An star that when clicked assigns the rating to the value of 1
     */
    private ImageView star1;
    /**
     * An star that when clicked assigns the rating to the value of 2
     */
    private ImageView star2;
    /**
     * An star that when clicked assigns the rating to the value of 3
     */
    private ImageView star3;
    /**
     * An star that when clicked assigns the rating to the value of 4
     */
    private ImageView star4;
    /**
     * An star that when clicked assigns the rating to the value of 5
     */
    private ImageView star5;
    /**
     * The rating that the user is giving to a coach or gym owner
     */
    private String rating;
    /**
     * A button that when clicked submits a new rating for a coach or a gym owner
     */
    private Button submit;
    /**
     * A button that when clicked exits out of the popup
     */
    private Button cancel;
    /**
     * Signifies what type of user is receiving a rating (coach or gym owner)
     */
    private String ratingType;
    /**
     * The id of the user that is being rated
     */
    private int id;
    /**
     * The JSON Object that represents the user
     */
    private JSONObject ratedObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_pop_up);
        rating = "3";
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width * .85),(int)(height * .55));


        star1 = findViewById(R.id.star1_rating_popup);
        star2 = findViewById(R.id.star2_rating_popup);
        star3 = findViewById(R.id.star3_rating_popup);
        star4 = findViewById(R.id.star4_rating_popup);
        star5 = findViewById(R.id.star5_rating_popup);
        submit = findViewById((R.id.submit_rating_rating_popup));
        cancel = findViewById(R.id.cancel_rating_rating_popup);

        ratingType = (String) getIntent().getSerializableExtra("ratingType");
        id = Integer.parseInt((String) getIntent().getSerializableExtra("id"));
        String requestURL;
        if(ratingType.equalsIgnoreCase("Coach"))
        {
            requestURL = Const.URL_JSON_REQUEST_COACHES + id;
        }
        else
        {
            requestURL = Const.URL_JSON_REQUEST_GYMOWNER + id;
        }
        ServerRequest request = new ServerRequest();
        request.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                ratedObject = result;
            }

            @Override
            public void onSuccess(JSONArray result) {

            }
        }, requestURL);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillStars(1);
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillStars(2);
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillStars(3);
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillStars(4);
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillStars(5);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerRequest ratingRequest = new ServerRequest();
                String url;
                if(ratingType.equalsIgnoreCase("Coach"))
                {
                    url = Const.URL_JSON_REQUEST_COACHES + id + "/rate?rating=" + rating;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rating", rating);
                    ratingRequest.jsonObjectRequest(url, 2, new JSONObject(params));
                }
                else if(ratingType.equalsIgnoreCase("GymOwner"))
                {
                    url = Const.URL_JSON_REQUEST_GYMOWNER + "/"+ id + "/rate?rating=" + rating;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rating", rating);
                    ratingRequest.jsonObjectRequest(url, 2, new JSONObject(params));
                }

                Toast.makeText(getApplicationContext(), "Rating submitted",
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

    private void fillStars(int star)
    {
        if(star == 1)
        {
            star1.setImageResource(R.drawable.ic_star_24);
            star2.setImageResource(R.drawable.ic_star_outline_24);
            star3.setImageResource(R.drawable.ic_star_outline_24);
            star4.setImageResource(R.drawable.ic_star_outline_24);
            star5.setImageResource(R.drawable.ic_star_outline_24);
            rating = "1";
        }
        else if(star == 2)
        {
            star1.setImageResource(R.drawable.ic_star_24);
            star2.setImageResource(R.drawable.ic_star_24);
            star3.setImageResource(R.drawable.ic_star_outline_24);
            star4.setImageResource(R.drawable.ic_star_outline_24);
            star5.setImageResource(R.drawable.ic_star_outline_24);
            rating = "2";
        }
        else if(star == 3)
        {
            star1.setImageResource(R.drawable.ic_star_24);
            star2.setImageResource(R.drawable.ic_star_24);
            star3.setImageResource(R.drawable.ic_star_24);
            star4.setImageResource(R.drawable.ic_star_outline_24);
            star5.setImageResource(R.drawable.ic_star_outline_24);
            rating = "3";
        }
        else if(star == 4)
        {
            star1.setImageResource(R.drawable.ic_star_24);
            star2.setImageResource(R.drawable.ic_star_24);
            star3.setImageResource(R.drawable.ic_star_24);
            star4.setImageResource(R.drawable.ic_star_24);
            star5.setImageResource(R.drawable.ic_star_outline_24);
            rating = "4";
        }
        else if(star == 5)
        {
            star1.setImageResource(R.drawable.ic_star_24);
            star2.setImageResource(R.drawable.ic_star_24);
            star3.setImageResource(R.drawable.ic_star_24);
            star4.setImageResource(R.drawable.ic_star_24);
            star5.setImageResource(R.drawable.ic_star_24);
            rating = "5";
        }
    }
}