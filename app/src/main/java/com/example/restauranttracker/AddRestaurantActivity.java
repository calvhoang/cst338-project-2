package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.databinding.ActivityAddRestaurantBinding;


public class AddRestaurantActivity extends AppCompatActivity {

    ActivityAddRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    static Intent addRestaurantActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AddRestaurantActivity.class);
        return intent;
    }
}
