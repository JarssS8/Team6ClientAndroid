package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LogOutActivity extends AppCompatActivity {

    //Button declaration
    private Button btLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out_activity);

        //Button Assignment
        btLogOut=findViewById(R.id.btLogOut);

    }

    public void logOut(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}
