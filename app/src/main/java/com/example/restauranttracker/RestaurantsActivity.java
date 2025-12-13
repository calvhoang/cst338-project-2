package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.databinding.ActivityRestaurantsBinding;

public class RestaurantsActivity extends AppCompatActivity {

    ActivityRestaurantsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    static Intent restaurantsActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, RestaurantsActivity.class);
        return intent;
    }
}
