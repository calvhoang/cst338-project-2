package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauranttracker.databinding.ActivityMainBinding;
import com.example.restauranttracker.viewHolders.AppAdapter;
import com.example.restauranttracker.viewHolders.AppViewModel;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private AppViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        RecyclerView recyclerView = binding.restaurantRecyclerView;
        final AppAdapter adapter = new AppAdapter(new AppAdapter.RestaurantDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getRestaurantsByUserId(loggedInUserId).observe(this, adapter::submitList);
        addRandomRestaurantsForVideo();
    }

    private void setupButtonNavigation() {
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

    private void addRandomRestaurantsForVideo() {
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Taco Bell", "Mexican", "San Jose", 3, false);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "McDonald's", "American", "San Francisco", 3, true);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Chipotle", "Mexican", "Davis", 4, false);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Olive Garden", "Italian", "San Jose", 3, false);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Happy Lamb Hotpot", "Mongolian", "Cupertino", 4, true);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Panda Express", "Chinese", "Fremont", 3, true);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "In-N-Out", "American", "Gilroy", 4, false);
        viewModel.insertRestaurantWithUser(
                loggedInUserId, "Din Tai Fung", "Taiwanese", "Santa Clara", 4, false);
    }
}