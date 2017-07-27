package com.example.asus.touristinfoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
* This activity is used for logging into the app.
* It accepts only user id = "user123@example.com" and password = "User@1234".
* This class also calls the "UserSessionManager" class for maintaing login status.
*
*/


public class LoginActivity extends AppCompatActivity {

    private EditText loginID,password;;
    private Button login;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //creates a new session
        session =new UserSessionManager(getApplicationContext());

        loginID = (EditText) findViewById(R.id.et_login_id);
        password = (EditText) findViewById(R.id.et_login_password);
        login = (Button) findViewById(R.id.bt_login);


        //on clicking login button the app first checks if the userId and password are
        //user123@example.com and User@1234 respectively.

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginID.getText().toString().equals("user123@example.com") && password.getText().toString().equals("User@1234"))
                {
                    session.createUserLoginSession("User Name","");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_LONG).show();

            }
        });
    }
}
