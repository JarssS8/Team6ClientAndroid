package com.example.androidapplication;


import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Menu_RecyclerViewAdapter extends RecyclerView.Adapter<Menu_RecyclerViewAdapter.MyViewHolder> {

    ArrayList<String> namesMenu;

    public Menu_RecyclerViewAdapter(ArrayList<String> namesMenu) {
        this.namesMenu = namesMenu;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_name,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Menu_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.setDatos(namesMenu.get(position));
    }

    @Override
    public int getItemCount() {
        return namesMenu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.txtNameMenu);
            icon=view.findViewById(R.id.iconMenu);

        }

        public void setDatos(String name) {
            this.name.setText(name);
        }
    }
}
