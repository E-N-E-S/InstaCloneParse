package com.enesb.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        usernameText = findViewById(R.id.sign_up_activity_name_text);
        passwordText = findViewById(R.id.sign_up_activity_password_text);

        ParseUser parseUser = ParseUser.getCurrentUser(); // Giris yapmis kullaniciyi al ve intent ile gönder.
        if (parseUser != null){
            // Intent -> FeedActivity
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        }
    }

    public void signIn(View view) {

        ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sign In Successful: " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    // Intent -> FeedActivity
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void signUp (View view) {

        ParseUser user = new ParseUser();
        user.setUsername(usernameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_SHORT).show();
                    // Intent -> FeedActivity
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}