package com.example.werqoutfrontend;

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
    private ImageView profilePicture;
    private TextView usernameText;
    private TextView userTypeText;
    private TextView emailText;
    private TextView bioText;
    private Bitmap thePic;

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

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileScreen.class));
            }
        });


    }
}