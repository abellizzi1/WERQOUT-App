package com.example.werqoutfrontend;
/**
 * This class contains the logic necessary for the profile screen to function. It basically
 * allows a user to view their current account information and other user related statistics depending
 * on the type of user.
 *
 * @author colin
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

import java.io.Serializable;


public class ProfileScreen extends AppCompatActivity implements Serializable {
    /**
     * This image view reperesent the users profile picture
     */
    private ImageView profilePicture;
    /**
     * A textview used to display the current user's Username
     */
    private TextView usernameText;
    /**
     * A textview used to display the type of user (Athlete, Coach, Gym Owner)
     */
    private TextView userTypeText;
    /**
     * A textview used to display used to display the user's email
     */
    private TextView emailText;
    /**
     * A textview used to display a user's bio
     */
    private TextView bioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        profilePicture = findViewById(R.id.profile_picture_profile_screen);
        usernameText = findViewById((R.id.Username_textview_profile_screen));
        userTypeText = findViewById(R.id.userType_text_profile_screen);
        emailText = findViewById((R.id.email_text_profile_screen));
        bioText = findViewById(R.id.bio_profileScreen);
        TextView idText = findViewById(R.id.userID_text_profile_screen);

        Button editProfile = findViewById(R.id.edit_profile_button_profile_screen);
        Button returnHome = findViewById(R.id.return_button_profile_screen);

        //If the profile screen is accessed from the search screen, display the profile image of the
        //selected user here
        if(getIntent().getSerializableExtra("calledFrom") != null)
        {
            editProfile.setVisibility(View.GONE);
            Athlete athlete = (Athlete) getIntent().getSerializableExtra("athlete");
            usernameText.setText(athlete.getUsername());
            idText.setText("User ID: " + athlete.getId());
            userTypeText.setText("User type: " + athlete.getUserType());
            emailText.setText("Email: " + athlete.getEmail());
            bioText.append("Successfully viewed from the search activity");
        }
        //If the profile screen is accessed from the home screen, then simply display the current
        //user's profile information
        else {
            usernameText.setText(User.currentUser.getUsername());
            idText.setText("User ID: " + User.currentUser.getId());
            userTypeText.setText("User type: " + User.currentUser.getUserType());
            emailText.setText("Email: " + User.currentUser.getEmail());
            bioText.append(" The one and only :)");
        }
        /*
        TODO: Once we are able to properly store images, replace this server request with the stored image
         */
        ServerRequest profilePic = new ServerRequest();
        profilePic.imageRequest(new VolleyCallbackImage() {
            @Override
            public void onSuccess(Bitmap response) {
                profilePicture.setImageBitmap(response);
            }
        }, Const.DEFAULT_PROFILE_PICTURE);

        //A button that takes a user to the edit profile screen
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Sends the user to the edit profile screen
             */
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileScreen.class));
            }
        });
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AthleteHomeScreen.class));
            }
        });


    }
}