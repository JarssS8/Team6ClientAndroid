package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements Button.OnClickListener {

    public Button btSignUpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btSignUpMain=findViewById(R.id.btSignUpMain2);
        btSignUpMain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHelp: {
                // Sacar el popup
            }
            case R.id.btSignUpMain2: {
                Intent intent = new Intent (this, SignUpActivity.class);
                startActivity(intent);
            }
        }
    }
}
