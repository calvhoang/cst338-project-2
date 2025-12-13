package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.databinding.ActivityAddRestaurantBinding;

public class AddRestaurantActivity extends AppCompatActivity {

    ActivityAddRestaurantBinding binding;
    AppRepository repository;

    String restaurantName = "";
    String cuisine = "";
    String city = "";
    int rating = 0;
    boolean visited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                insertRestaurant();
            }
        });
    }

    private void getUserInput() {
        restaurantName = binding.restaurantNameInput.getText().toString();
        cuisine = binding.restaurantCuisineInput.getText().toString();
        city = binding.restaurantCityInput.getText().toString();
        try {
            rating = Integer.parseInt(binding.restaurantRatingInput.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(MainActivity.TAG, "Error reading value from Rating EditText");
        }
        visited = binding.restaurantVisitedCheckbox.isChecked();
    }

    // TODO: insert into database method;
    private void insertRestaurant() {
        if (restaurantName.isEmpty()) {
            return;
        }


    }

    static Intent addRestaurantActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AddRestaurantActivity.class);
        return intent;
    }

}
