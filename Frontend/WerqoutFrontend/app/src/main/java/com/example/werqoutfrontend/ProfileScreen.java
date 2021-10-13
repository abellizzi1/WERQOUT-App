package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

public class ProfileScreen extends AppCompatActivity {
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        profilePicture = findViewById(R.id.profile_picture_profile_screen);

        ServerRequest profilePic = new ServerRequest();
        profilePic.imageRequest(new VolleyCallbackImage() {
            @Override
            public void onSuccess(Bitmap response) {
                profilePicture.setImageBitmap(response);
            }
        }, Const.HALLOWEEN);


    }
}