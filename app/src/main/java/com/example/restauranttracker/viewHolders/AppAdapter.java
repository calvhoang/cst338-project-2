package com.example.restauranttracker.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.restauranttracker.Database.RestaurantUserRestaurantJoin;

public class AppAdapter extends ListAdapter<RestaurantUserRestaurantJoin, AppViewHolder> {

    protected AppAdapter(@NonNull DiffUtil.ItemCallback<RestaurantUserRestaurantJoin> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AppViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        RestaurantUserRestaurantJoin current = getItem(position);
        holder.bind(current.toString());
    }

    public static class RestaurantDiff extends DiffUtil.ItemCallback<RestaurantUserRestaurantJoin> {
        @Override
        public boolean areItemsTheSame(@NonNull RestaurantUserRestaurantJoin oldItem, @NonNull RestaurantUserRestaurantJoin newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RestaurantUserRestaurantJoin oldItem, @NonNull RestaurantUserRestaurantJoin newItem) {
            return oldItem.equals(newItem);
        }
    }
}
