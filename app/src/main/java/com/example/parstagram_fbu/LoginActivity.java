package com.example.parstagram_fbu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parstagram_fbu.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
//    private EditText etUsername;
//    private EditText etPassword;
//    private Button btnLogin;

    private ActivityLoginBinding activityLoginBinding;
    public static  final String TAG = "LoginActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);

        // so the user does not have to login again when already signed in
        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }


        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClick login button");
                String username = activityLoginBinding.etUsername.getText().toString();
                String password = activityLoginBinding.etPassword.getText().toString();
                logInUser(username,password);
            }

            private void logInUser(String username, String password) {
                Log.i(TAG,"Attempting to login user " + username);

                // Navigate to the mainactivity if the login is successfull
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    // this is prefered to logIn because it logins in the background, and also used for better user experience
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(e != null){
                            Toast.makeText(LoginActivity.this,"Issue with Login",Toast.LENGTH_SHORT).show();

                            Log.e(TAG,"Issue with Login",e);
                            return;
                        }
                        //actually navigate to the main activity
                        goMainActivity();
                        Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();


                    }


                });
            }
        });


    }
    private void goMainActivity(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

}
