package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppRepository repository;

    public static final String TAG = "RESTAURANT_TRACKER_APP";

    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupButtonNavigation();
        loginUser();

        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        repository = AppRepository.getRepository(getApplication());
    }

    private void loginUser(){
        loggedInUserId = getIntent().getIntExtra(TAG, -1);
    }

    // TODO: set up intents & intent factories
    private void setupButtonNavigation() {
        binding.restaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RestaurantsActivity.restaurantsActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRestaurantActivity.addRestaurantActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RandomizeActivity.randomizeActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void setupAdminButton() {
        // TODO: check if user is admin
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAG, userId);
        return intent;
    }
}