package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private Button btLogOut;
    private TextView txtWelcome;
    private TextView txtUserData;
    private ArrayList<String> namesMenu;
    private RecyclerView recyclerNamesMenu;

    /**
     * First instance of components from this activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LogOut","Initilize of log out layout components");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out_activity);
        user= (User) getIntent().getSerializableExtra("this_user");
        btLogOut = findViewById(R.id.btLogOut);
        txtWelcome= findViewById(R.id.txtWelcome);
        txtUserData= findViewById(R.id.txtUser);
        txtWelcome.setText("Welcome, "+user.getFullName());
        txtUserData.setText("Email: "+user.getEmail()+"\nLast Access: "+user.getLastAccess());
        createRecyclerView();
    }

    /**
     * This method controls if the user wants LogOut of the application clicking on LogOut button
     * @param v Is a View who controls the event of the onClick action
     * @throws InterruptedException A exception that throws the join method
     */
    public void logOut(View v) throws InterruptedException {
        Log.i("LogOut","Click on logOut button.");
        SocketThread socketThread = new SocketThread();
        socketThread.setMessageType(LOGOUT_MESSAGE);
        Log.i("LogOut","Starting thread for update the last access");
        socketThread.start();
        Log.i("LogOut","Wait the other thread");
        socketThread.join();
        Intent intent = new Intent(this, LoginActivity.class);
        Log.i("LogOut","Going to LogInWindow");
        startActivity(intent);

    }

    /**
     * This method create a recycler view for future implementations
     */
    public void createRecyclerView(){
        Log.i("LogOut","Creating the recycler view");
        recyclerNamesMenu=findViewById(R.id.recyclerViewNamesMenu);
        recyclerNamesMenu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        namesMenu=new ArrayList<String>();
        for(int i=0;i<10;i++){
            namesMenu.add("Comming Soon!");
        }
        Menu_RecyclerViewAdapter adapter=new Menu_RecyclerViewAdapter(namesMenu);
        recyclerNamesMenu.setAdapter(adapter);
    }
}
