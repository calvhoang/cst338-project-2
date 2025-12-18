package com.example.restauranttracker.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.restauranttracker.Database.entities.RestaurantUserRestaurant;

public class AppAdapter extends ListAdapter<RestaurantUserRestaurant, AppViewHolder> {

    public AppAdapter(@NonNull DiffUtil.ItemCallback<RestaurantUserRestaurant> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AppViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        RestaurantUserRestaurant current = getItem(position);
        holder.bind(current.toString());
    }

    public static class RestaurantDiff extends DiffUtil.ItemCallback<RestaurantUserRestaurant> {
        @Override
        public boolean areItemsTheSame(@NonNull RestaurantUserRestaurant oldItem, @NonNull RestaurantUserRestaurant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RestaurantUserRestaurant oldItem, @NonNull RestaurantUserRestaurant newItem) {
            return oldItem.equals(newItem);
        }
    }
}
