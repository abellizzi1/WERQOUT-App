package com.example.appwithbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create_account_screen extends AppCompatActivity {
    private final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    private final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private EditText emailTxt;
    private EditText pass1Txt;
    private EditText pass2Txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);

//all of the text fields in the app
        emailTxt = findViewById(R.id.emailTxt);
        pass1Txt = findViewById(R.id.password1Txt);
        pass2Txt = findViewById(R.id.password2Txt);
        //button to go swtich to the welcome screen
        Button welcomeBtn = findViewById(R.id.createActBtn);

        welcomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidEmail()){
                    emailTxt.setError("Invalid Email");
                    emailTxt.requestFocus();
                }
                else if (!passWordsMatch(pass1Txt.toString(),pass2Txt.toString())){
                    pass1Txt.setError("Passwords do not match");
                    pass1Txt.requestFocus();
                }
                else if(!isValidPassword()){
                    pass1Txt.setError("Password must be eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
                    pass1Txt.requestFocus();
                }
                else{
                    startActivity(new Intent(Create_account_screen.this, welcomeScreen.class));
                }

            }
        });
    }
    /**
     * Checks wether the entered email is valid or not.
     * @return true if valid, false otherwise
     */
    public boolean isValidEmail() {
        String email = emailTxt.getText().toString();
        //compiles the regex pattern for the email;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        //checks to see if the email matches the pattern.
        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty()){
            emailTxt.setError("Email cant be empty");
            return false;
        }
        else{
            return matcher.matches();
        }

    }

    /**
     * Checks whether or not the passwords enter match and are valid
     * @param pas1 first password
     * @param pas2 second password
     * @return true if the match, false otherwise
     */
    public boolean passWordsMatch(String pas1, String pas2){
        if(pas2.equals(pas1)){
            return true;
        }
        return false;
    }

    /**
     * Checks if the passwords meet criteria
     * @return true if they meet the criteria, false otherwise.
     */
    public boolean isValidPassword(){
        String p1 = pass1Txt.toString();
        String p2 = pass2Txt.toString();
        //compiles the regex pattern for the password;
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        //checks to see if the password matches the pattern. Only need the first one because the already match.
        Matcher matcher = pattern.matcher(p1);
        if (passWordsMatch(p1,p2)){
            return matcher.matches();
        }
       return false;
    }
}