package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    //Buttons Declarations
    private Button btSignUp;
    private Button btLogIn;
    //Text View Declaration
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Button Association
        btSignUp = findViewById(R.id.btSignUpMain);
        btLogIn = findViewById(R.id.btLogInMain);

        //Text View Association
        username = findViewById(R.id.txtUsernameMain);
        password = findViewById(R.id.txtPasswordMain);


    }

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btLogInMain://Click on log in button

                if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10
                        && password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {

                    Snackbar.make(v, "El formato del usuario y la contraseña no es correcto", Snackbar.LENGTH_SHORT).show();

                } else if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10) {
                    Snackbar.make(v, "El formato del usuario no es correcto", Snackbar.LENGTH_SHORT).show();

                } else if (password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {
                    Snackbar.make(v, "El formato de la contraseña no es correcto", Snackbar.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(this, LogOutActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.btSignUpMain://Click on sign up button
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }

    }
}




