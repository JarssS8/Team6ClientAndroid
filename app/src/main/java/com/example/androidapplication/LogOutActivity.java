package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogOutActivity extends AppCompatActivity {

    //Button declaration
    private Button btLogOut;

    private ArrayList<String> namesMenu;
    private RecyclerView recyclerNamesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out_activity);

        //Button Assignment
        btLogOut = findViewById(R.id.btLogOut);

        createRecyclerView();



    }

    public void logOut(View v) {
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
