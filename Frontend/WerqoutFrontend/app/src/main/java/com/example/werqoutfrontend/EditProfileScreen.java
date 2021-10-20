package com.example.werqoutfrontend;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        TextView username = findViewById(R.id.edit_text_username_edit_profile);
        TextView email = findViewById(R.id.edit_email_edit_profilescreen);
        TextView password = findViewById(R.id.edit_text_password_edit_profile_screen);

        Button done = findViewById(R.id.done_button_edit_profile_screen);
        Button cancel = findViewById(R.id.cancel_button_edit_profile_screen);
        Button delete = findViewById(R.id.delete_button_edit_profile_screen);

        ImageView profilePic = findViewById(R.id.profile_picture_edit_profile_screen);

        username.setText(User.currentUser.getUsername());
        email.setText(User.currentUser.getEmail());
        password.setText(User.currentUser.getPassword());

        ServerRequest request = new ServerRequest();
        request.imageRequest(new VolleyCallbackImage() {
            @Override
            public void onSuccess(Bitmap response) {
                profilePic.setImageBitmap(response);
            }
        },Const.DEFAULT_PROFILE_PICTURE);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerRequest update = new ServerRequest();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(User.currentUser.getId()));
                params.put("userName", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                String url = Const.URL_JSON_GET_REQUEST + "/" + String.valueOf(User.currentUser.getId());
                JSONObject updatedProfile = new JSONObject(params);
                update.jsonObjectRequest(url,2,updatedProfile);

                User.currentUser.setUsername(username.getText().toString());
                User.currentUser.setEmail(email.getText().toString());
                User.currentUser.setPassword(password.getText().toString());
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileScreen.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileScreen.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder =
                        new androidx.appcompat.app.AlertDialog.Builder(EditProfileScreen.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Account?");
                builder.setMessage("This action cannot be undone");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAccount();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void deleteAccount()
    {
        ServerRequest deleteRequest = new ServerRequest();
        String url = Const.URL_JSON_GET_REQUEST + "/" + String.valueOf(User.currentUser.getId());
        deleteRequest.jsonObjectRequest(url,3,null);
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
    }
}