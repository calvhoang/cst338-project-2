package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.restauranttracker.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    public static final String TAG = "RESTAURANT_TRACKER_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreference();
        setupButtonNavigation();
    }

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

        repository.isUserAdmin(loggedInUserId).observe(this, isAdmin -> {
            if (Boolean.TRUE.equals(isAdmin)) {
                setupAdminButton();
            }
        });
    }

    private void setupAdminButton() {
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.adminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.adminButton.setEnabled(true);
        binding.adminButton.setVisibility(View.VISIBLE);
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}