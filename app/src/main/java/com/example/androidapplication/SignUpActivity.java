package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import utilities.beans.User;
import utilities.util.Util;

import static utilities.beans.Message.SIGNUP_MESSAGE;

public class SignUpActivity extends AppCompatActivity {

    private TextView lbCompleteFields;
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtFullName;
    private EditText txtPassword;
    private EditText txtRepeatPassword;
    private ImageButton btHelp;
    private Button btConfirm;
    private Button btGetIt;
    private String errorMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        lbCompleteFields = findViewById(R.id.lbCompleteFields);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtFullName = findViewById(R.id.txtFullName);
        txtPassword = findViewById(R.id.txtPassword);
        txtRepeatPassword = findViewById(R.id.txtRepeatPassword);
        btHelp = findViewById(R.id.btHelp);
        btConfirm = findViewById(R.id.btConfirm);

    }

    public boolean checkPassword(Editable password) {

        boolean capital = false;
        boolean number = false;
        boolean check = false;


        char passwordChar[] = password.toString().trim().toCharArray();
        if(password.toString().trim().length()!=0) {
            for (int i = 0; i < passwordChar.toString().trim().length() - 1; i++) {
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
        }
        return check;
    }

    private boolean checkPassRepeat(Editable password, Editable passwordRepeat) {

        boolean checkRepeat = false;
        if (password.toString().trim().equals(passwordRepeat.toString().trim())) {
            checkRepeat = true;
        }

        return checkRepeat;
    }

    private boolean checkEmail(Editable email) {

        boolean check = Util.validarEmail(email.toString());
        return check;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHelp: {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = layoutInflater.inflate(R.layout.popup_signup, null);
                final PopupWindow popupWindow = new PopupWindow(popUpView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                btGetIt = popUpView.findViewById(R.id.btGetIt);
                btGetIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;

            }
            case R.id.btConfirm:
                errorMessage="";
                boolean passCheck = checkPassword(txtPassword.getText());
                boolean passCheckRepeat = checkPassRepeat(txtPassword.getText(),
                        txtRepeatPassword.getText());
                boolean emailCheck = checkEmail(txtEmail.getText());

                boolean userLength = false;
                boolean passLength = false;
                boolean passRepeat = false;
                boolean passCorrect = false;
                boolean emailCorrect = false;
                //comprobar el tamaño del login
                if (txtUsername.length() >= 4 && txtUsername.length() <= 10) {
                    userLength = true;
                }
                else{
                    errorMessage+="Username invalid format\n";
                }
                //comprobar el tamaño de la contraseña
                if (txtPassword.length() >= 8 && txtPassword.length() <= 14) {
                    passLength = true;
                }else{
                    errorMessage+="Password invalid format\n";
                }

                if (passCheck) {
                    passCorrect = true;
                } else{
                    errorMessage+="Password must have an upper case and a number\n";
                }

                if (passCheckRepeat) {
                    passRepeat = true;
                }else{
                    errorMessage+="Password and repeat password don't match \n";
                }

                if (emailCheck) {
                    emailCorrect = true;
                }else{
                    errorMessage+="Email invalid format\n";
                }

                if (userLength && passLength && passCorrect && passRepeat && emailCorrect) {
                    User user = new User();
                    user.setPassword(txtPassword.getText().toString().trim());
                    user.setLogin(txtUsername.getText().toString().trim());
                    user.setEmail(txtEmail.getText().toString().trim());
                    user.setFullName(txtFullName.getText().toString().trim());
                    try {
                        SocketThread socketThread = new SocketThread();
                        socketThread.setUser(user);
                        socketThread.setMessageType(SIGNUP_MESSAGE);
                        socketThread.start();
                        socketThread.join();
                        switch (socketThread.getMessageType()) {
                            case "LoginTaken":
                                Snackbar.make(v,"Login already taken. Try it again.",Snackbar.LENGTH_SHORT).show();
                                break;
                            case "ServerError":
                                Snackbar.make(v,"Error connecting to the server",Snackbar.LENGTH_SHORT).show();
                                break;
                            case "OK":
                                Intent intent = new Intent(this, LoginActivity.class);
                                intent.putExtra("user", socketThread.getUser());
                                startActivity(intent);
                                break;
                        }
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }
                else{
                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popUpView = layoutInflater.inflate(R.layout.popup_signup, null);
                    final PopupWindow popupWindow = new PopupWindow(popUpView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    btGetIt = popUpView.findViewById(R.id.btGetIt);
                    TextView lbPopUp= popUpView.findViewById(R.id.lbPopUp);
                    lbPopUp.setText(errorMessage);
                    btGetIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
                break;
        }
    }

}
