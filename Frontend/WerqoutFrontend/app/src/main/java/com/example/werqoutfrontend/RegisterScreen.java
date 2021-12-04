package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The RegisterScreen class gives functionality to the register_screen.xml screen. It includes many
 * edit texts where the user can input their information to register. Once the user fills out all of
 * the fields, this RegisterScreen class validates all of the information and either posts to the database,
 * or gives the user an error.
 * @author Angelo Bellizzi
 * @author Colin Brenizer
 */
public class RegisterScreen extends AppCompatActivity {

    /**
     * The inputted first name.
     */
    private String firstName;

    /**
     * The inputted last name.
     */
    private String lastName;

    /**
     * The inputted email.
     */
    private String email;

    /**
     * The inputted password. Must follow the requirements for a password.
     */
    private String password;

    /**
     * The inputted confirm password, used to confirm the previous password.
     */
    private String confirmPassword;

    private Map<String, String> params;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to register_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.typeUser_spinner_register);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(RegisterScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);


        Button submitRegisterButton = findViewById(R.id.register_button_register);
        EditText firstNameInput = findViewById(R.id.firstName_input_register);
        EditText lastNameInput = findViewById(R.id.lastName_input_register);
        EditText emailInput = findViewById(R.id.email_input_register);
        EditText passwordInput = findViewById(R.id.password_input_register);
        EditText confirmPasswordInput = findViewById(R.id.confirmPassword_input_register);

        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function validates the fields on the screen and either posts a new user
             * to the database, or pops up with an error message containing information on the incorrect inputs.
             * @param v
             */
            @Override
            public void onClick(View v) {
                firstName = firstNameInput.getText().toString();
                lastName = lastNameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                confirmPassword = confirmPasswordInput.getText().toString();
                TextView errorMessage = findViewById(R.id.error_message_register);
                String errorMsg = "";
                errorMessage.setText("");

                if (!isValidName(firstName))
                {
                    errorMsg += "First name ";
                }
                if (!isValidName(lastName))
                {
                    errorMsg += "Last name ";
                }
                if (!isValidEmail(email))
                {
                   errorMsg += "Email ";
                }
                if (!isValidPassword(password) || !password.equals(confirmPassword)) {
                    errorMsg += "Password ";
                }
                if (userSpinner.getSelectedItem().toString().equals("Select type of user..."))
                {
                    errorMsg += "Dropdown ";
                }
                if (errorMsg.length() > 0)
                {
                    errorMsg += "invalid";
                    errorMessage.setText(errorMsg);
                }
                //if all fields are filled out properly, then we can send
                //a post request to the server.
                else
                {
                    //Storing the values in a hashmap to be placed in JSON
                    params = new HashMap<>();
                    params.put("userName", firstName);
                    params.put("lastName", lastName);
                    params.put("email", email);
                    params.put("password", password);

                    //Send the post request to the server
                    if (userSpinner.getSelectedItem().toString().equals("Athlete"))
                    {
                        Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES;
                    }
                    else if (userSpinner.getSelectedItem().toString().equals("Coach"))
                    {
                        Const.CURRENT_URL = Const.URL_JSON_REQUEST_COACHES;
                    }
                    else
                    {
                        Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER;
                        ServerRequest getGymOwnerLength = new ServerRequest();
                        getGymOwnerLength.jsonArrayRequest(new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                            }
                            @Override
                            public void onSuccess(JSONArray users) {
                                params.put("gymName", ("Gym " + (users.length() + 1)));
                            }
                        },Const.CURRENT_URL);
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            postUser(v);
                        }
                    }, 300);
                }
            }
        });
    }

    private void postUser(View v)
    {
        Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER + "/";
        ServerRequest request = new ServerRequest();
        request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
        startActivity(new Intent(v.getContext(), LoginScreen.class));
    }

    /**
     * Checks if the inputted password is valid:
     * Must be between 8 and 16 characters in length,
     * must have at least one number, special character,
     * uppercase letter, and lowercase letter.
     * @param pass
     * the inputted password
     * @return
     * returns true if the password is valid, else false.
     */
    public boolean isValidPassword(String pass)
    {
        return pass.length() >= 8 && pass.length() <= 16 && pass.matches("(.*[0-9].*)") && pass.matches("(.*[A-Z].*)") && pass.matches("^(?=.*[@#!$%^&+=*()~_{}|:;<>]).*$");
    }

    /**
     * Checks if the inputted email is valid:
     * Must contain characters before and after '@',
     * must contain characters before and after '.'
     * @param email
     * the inputted email
     * @return
     * returns true if the email is valid, else false.
     */
    public boolean isValidEmail(String email)
    {
        String[] emailSplit = email.split("@");
        if (emailSplit.length == 2 && isAlphaNumeric(emailSplit[0]))
        {
            String[] periodSplit = emailSplit[1].split("\\.");
            if (periodSplit.length == 2 && isAlphaNumeric(periodSplit[0] + periodSplit[1]))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the inputted name is valid:
     * Must contain only letters.
     * @param name
     * the inputted name
     * @return
     * returns true if the name is valid, else false.
     */
    public boolean isValidName(String name)
    {
        return name.matches("[a-zA-Z]+");
    }

    /**
     * Checks if the inputted string is AlphaNumeric.
     * @param s
     * the inputted string
     * @return
     * returns true if the string is AlphaNumeric, else false.
     */
    public boolean isAlphaNumeric(String s)
    {
        return s.matches("^[a-zA-Z0-9]+$");
    }
}
