package com.example.androidapplication;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import utilities.util.Util;

public class SignUpActivity extends AppCompatActivity implements Button.OnClickListener {

    private TextView lbCompleteFields;
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtFullName;
    private EditText txtPassword;
    private EditText txtRepeatPassword;
    private ImageButton btHelp;
    private Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        lbCompleteFields=findViewById(R.id.lbCompleteFields);
        txtUsername=findViewById(R.id.txtUsername);
        txtEmail=findViewById(R.id.txtEmail);
        txtFullName=findViewById(R.id.txtFullName);
        txtPassword=findViewById(R.id.txtPassword);
        txtRepeatPassword=findViewById(R.id.txtRepeatPassword);
        btHelp=findViewById(R.id.btHelp);
        btConfirm=findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(this);
        //Toast.makeText(this, "Boton: "+ botonConfirmar, Toast.LENGTH_SHORT).show();
    }

    private boolean checkPassword(Editable password){

        boolean capital = false;
        boolean number = false;
        boolean check = false;

        for(int i=0;i<password.length();i++){
            char ch = password.charAt(i);
            if(Character.isDigit(ch)){
                number=true;
            }
            if(Character.isUpperCase(ch)){
                capital=true;
            }
        }
        if(capital && number){
            check=true;
        }
        return check;
    }

    private boolean checkPassRepeat(Editable password, Editable passwordRepeat){

        boolean checkRepeat=false;
        if(password.equals(passwordRepeat)){
            checkRepeat=true;
        }

        return checkRepeat;
    }

    private boolean checkEmail(Editable email){

        boolean check = Util.validarEmail(email.toString());
        return check;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHelp: {
                // Sacar el popup
            }
            case R.id.btConfirm: {
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
                if(txtUsername.length()>4 && txtUsername.length()<10){
                    userLength = true;
                }
                //comprobar el tamaño de la contraseña
                if(txtPassword.length()>8 && txtPassword.length()<14){
                    passLength = true;
                }

                if(passCheck){
                    passCorrect = true;
                }

                if(passCheckRepeat){
                    passRepeat = true;
                }

                if(emailCheck){
                    emailCorrect = true;
                }

                if(userLength && passLength && passCorrect && passRepeat && emailCorrect){
                    //Mandar los datos.

                }
            }
        }
    }

}
