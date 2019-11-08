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

    /**
     * This method assign a ArrayList of the menu names to a class arraylist variable
     * @param namesMenu An ArrayList for assign on this class
     */
    public Menu_RecyclerViewAdapter(ArrayList<String> namesMenu) {
        this.namesMenu = namesMenu;
    }

    /**
     * Creates the holder of the recycler view with the layout of the view holder
     * @param parent The parent of this view
     * @param viewType A int who difines the type of this view
     * @return A view holder thats the recycler view is going to use
     */
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_name,null,false);
        return new MyViewHolder(view);
    }

    /**
     * Assign ArrayList data to the view holder
     * @param holder The viewholder class
     * @param position The possition of the arraylist for assign the data
     */
    @Override
    public void onBindViewHolder( Menu_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.setDatos(namesMenu.get(position));
    }

    /**
     * The count of all the elements in the arraylist
     * @return A int with the number of elements
     */
    @Override
    public int getItemCount() {
        return namesMenu.size();
    }

    /**
     * This is the view holder class from the recycler view. The view holder assign the data to the layout.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.txtNameMenu);
            icon=view.findViewById(R.id.iconMenu);

        }

        /**
         * Assign data to the layout components
         * @param name A string of one name fron the ArrayList
         */
        public void setDatos(String name) {
            this.name.setText(name);
        }
    }
}
