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
    private Button btSignUp;
    private Button btLogIn;
    private EditText username;
    private EditText password;
    private User user = null;
    private boolean justSignUp = false;

    /**
     * First instance of components from this activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LogIn","Initilize of log in layout components");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btSignUp = findViewById(R.id.btSignUpMain);
        btLogIn = findViewById(R.id.btLogInMain);
        username = findViewById(R.id.txtUsernameMain);
        password = findViewById(R.id.txtPasswordMain);
        Log.i("Login","Try to get user from sign up activity");
        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            Log.i("Login","Gets user from sign up activity. Putting values on login and password fields.");
            username.setText(user.getLogin());
            password.setText(user.getPassword());
            justSignUp = true;
        }


    }

    /**
     * This method control action onClick when the user press one button in this layout
     * @param v Is a View who controls the event of the onClick action
     */
    public void onClick(View v) {
        Log.i("Login","User clicks on one component of the app");
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btLogInMain:
                Log.i("Login","Click on login main button");
                if (justSignUp) {
                    Log.i("Login","User just sign up. Intent to logout don't going to DataBase");
                    intent = new Intent(this, LogOutActivity.class);
                    intent.putExtra("this_user", user);
                    startActivity(intent);
                    this.finish();
                }
                Log.i("Login","Check if the login and password could be correct");
                if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10
                        && password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {

                    Snackbar.make(v, "Username and password format are not correct", Snackbar.LENGTH_SHORT).show();

                } else if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() > 10) {
                    Snackbar.make(v, "Username format it's not correct", Snackbar.LENGTH_SHORT).show();

                } else if (password.getText().toString().trim().length() < 8 || password.getText().toString().trim().length() > 14) {
                    Snackbar.make(v, "Password format it's not correct", Snackbar.LENGTH_SHORT).show();
                } else if (!checkNumberUpperPass()) {
                    Snackbar.make(v, "Password format it's not correct", Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.i("Login","Fields could be correct. Checking connection");
                    if (isConnected()) {
                        Log.i("Login","User is connected to internet");
                        User user = new User();
                        user.setLogin(username.getText().toString().trim());
                        user.setPassword(password.getText().toString().trim());
                        try {
                            Log.i("Login","Create the thread class for interact with the socket");
                            SocketThread socketThread = new SocketThread();
                            socketThread.setMessageType(LOGIN_MESSAGE);
                            socketThread.setUser(user);
                            socketThread.start();
                            Log.i("Login","Thread started and wait in LoginActivity to SocketThread finish");
                            socketThread.join();
                            //Exceptions control
                            switch (socketThread.getMessageType()) {
                                case "LoginError":
                                    Snackbar.make(v, "Login not found", Snackbar.LENGTH_SHORT).show();
                                    Log.e("Login","Login not exits on DataBase");
                                    break;

                                case "ServerError":
                                    Snackbar.make(v, "Error connecting to the server", Snackbar.LENGTH_SHORT).show();
                                    Log.e("Login","Error with the server");
                                    break;
                                case "PasswordError":
                                    Snackbar.make(v, "Password is wrong", Snackbar.LENGTH_SHORT).show();
                                    Log.e("Login","Password is not correct");
                                    break;
                                case "OK":
                                    if (socketThread.getUser() != null) {
                                        Log.i("Login","Correct login, going to logout activity");
                                        intent = new Intent(this, LogOutActivity.class);
                                        intent.putExtra("this_user", socketThread.getUser());
                                        startActivity(intent);
                                        this.finish();
                                    }
                                    break;
                            }
                        } catch (InterruptedException e) {
                            Log.e("Login","Thread join error "+e.getMessage());
                        }
                    } else {
                        Log.i("Login","User is connected to internet");
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

            case R.id.btSignUpMain:
                Log.i("Login","Click on SignUp button");
                if (isConnected()) {
                    Log.i("Login","User has internet connection. Going to sign up window");
                    intent = new Intent(this, SignUpActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("Login","User hasn't internet connection");
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

    /**
     * This methos check if the password contains at least one upper case and one number for the validation
     * @return A boolean affirmative if the validations are correct
     */
    private boolean checkNumberUpperPass() {
        boolean capital = false;
        boolean number = false;
        boolean check = false;
        char[] passwordChar = password.getText().toString().trim().toCharArray();
        for (int i = 0; i < password.getText().toString().trim().length()-1; i++) {
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

    /**
     * This method checks if the connection with internet is available
     * @return A boolean affirmative if the validations are correct
     */
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




