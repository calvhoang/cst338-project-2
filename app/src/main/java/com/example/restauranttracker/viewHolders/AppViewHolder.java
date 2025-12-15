package com.example.restauranttracker.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauranttracker.R;

public class AppViewHolder extends RecyclerView.ViewHolder {

    private final TextView restaurantViewItem;

    public AppViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantViewItem = itemView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text) {
        restaurantViewItem.setText(text);
    }

    static AppViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_recycler_item, parent, false);
        return new AppViewHolder(view);
    }

}
