package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;

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

        username.setText(User.currentUser.getUsername());
        email.setText(User.currentUser.getEmail());
        password.setText(User.currentUser.getPassword());

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
    }
}