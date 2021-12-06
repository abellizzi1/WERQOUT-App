package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.model.Coach;
import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.network.Websocket;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the logic necessary for the profile screen to function. It basically checks
 * the email and the password that the user enters to login to see whether or not the user has
 * provided a valid login. If the user enters a valid login they are navigated to the home screen
 * @author Angelo
 * @author Colin
 */
public class LoginScreen extends AppCompatActivity {
    /**
     * The textview where the user inputs their email
     */
    private TextView emailInput;
    /**
     * The textview where the user inputs their password
     */
    private TextView passwordInput;
    /**
     * The email received from the backend
     */
    private String emailResponse;
    /**
     * The password recieved from the backend
     */
    private String passwordResponse;
    /**
     * The text retrieved from the emailInput text view
     */
    private String email;
    /**
     * The password retrieved from the passwordInput text view
     */
    private String password;
    /**
     * Provides a drop down menu from which the user can select which type of user to login as
     * (Athlete, Coach, or Gym Owner)
     */
    private Spinner userSpinner;

    /**
     * Stores the type of user as an integer(Athlete = 0, Coach = 1, and Gym Owner = 2)
     */
    private int userType;

    /**
     * The username of the user
     */
    private static String firstName;
    /**
     * The id of the user
     */
    private static int id;

    private String TAG = ServerRequest.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        userSpinner = (Spinner) findViewById(R.id.typeUser_spinner_login);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(LoginScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        Button submitButton_login = findViewById(R.id.submit_button_login);
        Button registerButton_login = findViewById(R.id.register_button_login);
        emailInput = findViewById(R.id.email_input_login);
        passwordInput = findViewById(R.id.password_input_login);

        registerButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Takes the user to the register screen when clicked
             */
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CoachGroupsScreen.class));
            }

        });

        submitButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Validates the user's login information on click
             */
            public void onClick(View view) {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
//            startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
                getJsonLoginInfo();
            }
        });

    }

    private void getJsonLoginInfo()
    {
        if (userSpinner.getSelectedItem().toString().equals("Athlete"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES + "all";
            userType = 0;
        }
        else if (userSpinner.getSelectedItem().toString().equals("Coach"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_COACHES;
            userType = 1;
        }
        else
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER;
            userType = 2;
        }

        ServerRequest userLogin = new ServerRequest();
        userLogin.jsonArrayRequest(new VolleyCallback() {
            //Is there a way that I don't need to include this method?
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        //Check to see if the credentials entered match
                        //any existing users
                        JSONObject user = users.getJSONObject(i);
                        if(user.get("email").toString().equals(email))
                        {
                            getUserInfo(user);
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                login();
            }
        },Const.CURRENT_URL);
    }

    private void login()
    {
        if (!userSpinner.getSelectedItem().toString().equals("Select type of user...") && emailResponse != null)
        {
            if(emailResponse != null && emailResponse.equalsIgnoreCase(email)
                    && passwordResponse.equals(password))
            {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                if (userSpinner.getSelectedItem().toString().equals("Athlete"))
                {
                    Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES;
                    new Websocket();
                    startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
                }
                else if (userSpinner.getSelectedItem().toString().equals("Coach"))
                {
                    startActivity(new Intent(getApplicationContext(), CoachHomeScreen.class));
                }
                else {
                        // startActivity Gym owner home screen
                    }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Type of user not selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Returns the first name of the user
     * @return
     *  The first name of the user
     */
    public static String getFirstName() { return firstName; }

    /**
     * Returns the id of the user
     * @return
     *  The id of the user
     */
    public static int getId() { return id; }

    private void getUserInfo(JSONObject user)
    {
        try{
            emailResponse = user.get("email").toString();
            passwordResponse = user.get("password").toString();
            firstName = user.get("userName").toString();
            id = ((int)user.get("id"));

            if(userType == 0)
            {
                Athlete athlete = new Athlete(emailResponse,passwordResponse,firstName,id);
            }
            else if(userType == 1 || userType == 2)
            {
                double ratingResponse = user.getDouble("rating");
                int numRatingsResponse = user.getInt("numRatings");
                if(userType == 2)
                {
                    String gymName = user.getString("gymName");
                }
                else{
                    new Coach(emailResponse, passwordResponse, firstName, id,
                            ratingResponse, numRatingsResponse);
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}