package com.example.androidapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Menu_RecyclerViewAdapter extends RecyclerView.Adapter<Menu_RecyclerViewAdapter.MyViewHolder> {


        private ArrayList<String> menuNames;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            public TextView lbName;


            public MyViewHolder(TextView v) {
                super(v);
                lbName = v.findViewById(R.id.txtNameMenu);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public Menu_RecyclerViewAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public Menu_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_name, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element


            holder.lbName.setText(menuNames.get(position).toString());


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return menuNames.size();
        }

}
