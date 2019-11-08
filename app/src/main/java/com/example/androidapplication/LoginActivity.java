package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import utilities.beans.User;
import utilities.exception.DBException;
import utilities.exception.LogicException;
import utilities.exception.LoginNotFoundException;
import utilities.exception.WrongPasswordException;

import static utilities.beans.Message.LOGIN_MESSAGE;

public class LoginActivity extends AppCompatActivity implements Button.OnClickListener {

    public Button btSignUpMain;

    //Buttons Declarations
    private Button btSignUp;
    private Button btLogIn;
    //Text View Declaration
    private EditText username;
    private EditText password;

    private User user = null;

    private boolean justSignUp = false;


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
        isConnected();
        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            username.setText(user.getLogin());
            password.setText(user.getPassword());
            justSignUp = true;
        }


    }

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btLogInMain://Click on log in button
                if (justSignUp) {
                    intent = new Intent(this, LogOutActivity.class);
                    intent.putExtra("this_user", user);
                    startActivity(intent);
                    this.finish();
                }
                if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10
                        && password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {

                    Snackbar.make(v, "El formato del usuario y la contraseña no es correcto", Snackbar.LENGTH_SHORT).show();

                } else if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10) {
                    Snackbar.make(v, "El formato del usuario no es correcto", Snackbar.LENGTH_SHORT).show();

                } else if (password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {
                    Snackbar.make(v, "El formato de la contraseña no es correcto", Snackbar.LENGTH_SHORT).show();
                } else if (!checkNumberUpperPass()) {
                    Snackbar.make(v, "El formato de la contraseña no es correcto", Snackbar.LENGTH_SHORT).show();

                } else {
                    if (isConnected()) {
                        User user = new User();
                        user.setLogin(username.getText().toString().trim());
                        user.setPassword(password.getText().toString().trim());
                        try {
                            SocketThread socketThread = new SocketThread();
                            socketThread.setMessageType(LOGIN_MESSAGE);
                            socketThread.setUser(user);
                            Log.d("Main", "onClick: PreStart Thread");
                            socketThread.start();
                            Log.d("Main", "onClick: Join thread ");
                            socketThread.join();
                            Log.d("Main", "After join");
                            switch (socketThread.getMessageType()) {
                                case "LoginError":
                                    Snackbar.make(v, "Login not found", Snackbar.LENGTH_SHORT).show();
                                    break;

                                case "ServerError":
                                    Snackbar.make(v, "Error connecting to the server", Snackbar.LENGTH_SHORT).show();
                                    break;
                                case "PasswordError":
                                    Snackbar.make(v, "Password is wrong", Snackbar.LENGTH_SHORT).show();
                                    break;
                                case "OK":
                                    if (socketThread.getUser() != null) {
                                        intent = new Intent(this, LogOutActivity.class);
                                        intent.putExtra("this_user", socketThread.getUser());
                                        startActivity(intent);
                                        this.finish();
                                    }
                                    break;
                            }
                        } catch (InterruptedException e) {
                            e.getMessage();
                        }


                    } else {
                        // Snackbar.make(v, "Check your network status", Snackbar.LENGTH_SHORT).show();
                        final Snackbar snackbar = Snackbar.make(v, "NO CONNECTION, CHECK YOUR CONNECTION", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();

                    }
                }

                break;

            case R.id.btSignUpMain://Click on sign up button
                if (isConnected()) {
                    intent = new Intent(this, SignUpActivity.class);
                    startActivity(intent);
                } else {
                    // Snackbar.make(v, "Check your network status", Snackbar.LENGTH_SHORT).show();
                    final Snackbar snackbar = Snackbar.make(v, "NO CONNECTION, CHECK YOUR CONNECTION", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();

                }


                break;
        }

    }

    private boolean checkNumberUpperPass() {
        boolean capital = false;
        boolean number = false;
        boolean check = false;


        char passwordChar[] = password.getText().toString().trim().toCharArray();
        for (int i = 0; i < passwordChar.toString().trim().length()-1; i++) {
            if (!number)
                if (Character.isDigit(passwordChar[i])) {
                    number = true;
                }
            if (!capital)
                if (Character.isUpperCase(passwordChar[i])) {
                    capital = true;
                }
            if (capital && number)
                break;
        }
        if (capital && number) {
            check = true;
        }

        return check;
    }

    public boolean isConnected() {
        boolean connection = false;
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            connection = activeNetworkInfo != null && activeNetworkInfo.isConnected();


        } catch (Exception e) {
            Log.e("Connection", e.getMessage());
        }
        return connection;
    }
}




