package com.example.restauranttracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.databinding.ActivityAddRestaurantBinding;

public class AddRestaurantActivity extends AppCompatActivity {

    ActivityAddRestaurantBinding binding;

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

}
