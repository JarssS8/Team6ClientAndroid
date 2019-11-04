package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import utilities.beans.User;

import static utilities.beans.Message.LOGOUT_MESSAGE;

public class LogOutActivity extends AppCompatActivity {

    private User user;
    //Button declaration
    private Button btLogOut;

    private TextView txtWelcome;

    private TextView txtUserData;

    private ArrayList<String> namesMenu;
    private RecyclerView recyclerNamesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out_activity);
        //Assign the user
        user= (User) getIntent().getSerializableExtra("this_user");
        //Button Assignment
        btLogOut = findViewById(R.id.btLogOut);

        //Label Assignment
        txtWelcome= findViewById(R.id.txtWelcome);
        txtUserData= findViewById(R.id.txtUser);

        //Personal informetion for the user
        txtWelcome.setText("Welcome, "+user.getFullName());
        txtUserData.setText(user.getEmail()+"  "+user.getLastAccess());
        
        createRecyclerView();

    }

    public void logOut(View v) throws InterruptedException {
        SocketThread socketThread = new SocketThread();
        socketThread.setMessageType(LOGOUT_MESSAGE);
        socketThread.start();
        socketThread.join();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void createRecyclerView(){
        recyclerNamesMenu=findViewById(R.id.recyclerViewNamesMenu);
        recyclerNamesMenu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        namesMenu=new ArrayList<String>();
        for(int i=0;i<4;i++){
            namesMenu.add("Comming Soon!");
        }

        Menu_RecyclerViewAdapter adapter=new Menu_RecyclerViewAdapter(namesMenu);
        recyclerNamesMenu.setAdapter(adapter);
    }
}
