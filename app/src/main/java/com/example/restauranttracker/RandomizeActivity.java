package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.databinding.ActivityRandomizeBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;


public class RandomizeActivity extends BaseActivity {

    ActivityRandomizeBinding binding;
    AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRandomizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Randomize");
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), MainActivity.LOGGED_OUT);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);


        // TODO: RandomizeActivity
        appViewModel.getRandomRestaurantByUserId(loggedInUserId).observe(this, restaurant -> {

        });

        binding.randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    static Intent randomizeActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, RandomizeActivity.class);
        return intent;
    }
}
