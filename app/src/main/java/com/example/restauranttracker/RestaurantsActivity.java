package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauranttracker.databinding.ActivityRestaurantsBinding;
import com.example.restauranttracker.viewHolders.AppAdapter;
import com.example.restauranttracker.viewHolders.AppViewModel;

public class RestaurantsActivity extends BaseActivity {

    ActivityRestaurantsBinding binding;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), MainActivity.LOGGED_OUT);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        RecyclerView recyclerView = binding.restaurantRecyclerView;
        final AppAdapter adapter = new AppAdapter(new AppAdapter.RestaurantDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appViewModel.getRestaurantsByUserId(loggedInUserId).observe(this, adapter::submitList);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Restaurants");
        }

        binding.addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRestaurantActivity.addRestaurantActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    static Intent restaurantsActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, RestaurantsActivity.class);
        return intent;
    }
}
