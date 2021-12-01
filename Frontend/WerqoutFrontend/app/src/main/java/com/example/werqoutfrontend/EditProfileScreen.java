package com.example.werqoutfrontend;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.BitmapStringConversion;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to handle basic account management functionality. Users can choose to update
 * basic information such as their username, email, profile picture, and password. The user can also
 * choose to delete their account on this page
 * @author Colin
 */
public class EditProfileScreen extends AppCompatActivity {
    /**
     * An image view used for displaying a user's profile picture
     */
    private ImageView profilePic;
    /**
     * A bitmap used to represent a newly selected profile picture. If the user choose to confirm their
     * changes, then this bitmap becomes the user's new profile picture
     */
    private Bitmap bitmap;
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
        Button editPicture = findViewById(R.id.change_profile_picture_button_edit_profile);

        profilePic = findViewById(R.id.profile_picture_edit_profile_screen);

        username.setText(User.currentUser.getUsername());
        email.setText(User.currentUser.getEmail());
        password.setText(User.currentUser.getPassword());

        //When we are able to store a profile picture, replace this request with
        //the users stored image
        ServerRequest request = new ServerRequest();
        request.imageRequest(new VolleyCallbackImage() {
            @Override
            public void onSuccess(Bitmap response) {
                profilePic.setImageBitmap(response);
            }
        },Const.DEFAULT_PROFILE_PICTURE);
        /*
        Once the done button is clicked, a PUT request is created using the
        inputs of the textfields.
         */
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onClick is used to handle sending a put request and updating the users information]
             * on the backend
             */
            public void onClick(View view) {
                //Update information on the backend
                ServerRequest update = new ServerRequest();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(User.currentUser.getId()));
                params.put("userName", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                if(bitmap != null) {
                    String picture = BitmapStringConversion.getStringFromBitmap(bitmap);
                    params.put("userName", picture);
                }
                String url = Const.CURRENT_URL + String.valueOf(User.currentUser.getId());
                JSONObject updatedProfile = new JSONObject(params);
                update.jsonObjectRequest(url,2,updatedProfile);

                //Update information locally as well
                User.currentUser.setUsername(username.getText().toString());
                User.currentUser.setEmail(email.getText().toString());
                User.currentUser.setPassword(password.getText().toString());
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileScreen.class));
            }
        });
        /*
        This button returns the user to the previous activity
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick abandons changes made by the user and sends them back to their profile
             * screen
             */
            public void onClick(View view) {
                finish();
            }
        });
        /*
        This button deletes the current user from the data base, a pop will
        appear prompting the user to confirm that they want to delete their account
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick listener is used to delete the users account. A pop up will appear asking
             * for confirmation, at which point the user can confirm the delete and they will be taken
             * back to the login screen
             */
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
        /*
        This button takes the user to their stored photo and allows them to
        select an image to use as a profile picture
         */
        editPicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        }));
    }
    /*
    This helper method sends a DELETE request to the server and removes
    the user from the database
     */
    private void deleteAccount()
    {
        ServerRequest deleteRequest = new ServerRequest();
        String url = Const.CURRENT_URL + String.valueOf(User.currentUser.getId());
        deleteRequest.jsonObjectRequest(url,3,null);
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}