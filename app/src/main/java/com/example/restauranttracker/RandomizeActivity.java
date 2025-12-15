package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.databinding.ActivityRandomizeBinding;


public class RandomizeActivity extends AppCompatActivity {

    ActivityRandomizeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRandomizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Randomize");
        }
    }

    static Intent randomizeActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, RandomizeActivity.class);
        return intent;
    }
}
