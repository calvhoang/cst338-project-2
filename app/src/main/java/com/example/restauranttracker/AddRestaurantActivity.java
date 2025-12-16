package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.databinding.ActivityAddRestaurantBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;

public class AddRestaurantActivity extends AppCompatActivity {

    private ActivityAddRestaurantBinding binding;
    private AppViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Restaurant");
        }

        binding.addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                insertRestaurantInfo();
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

    private void insertRestaurantInfo() {
        if (restaurantName.isEmpty()) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(getString(R.string.preference_userId_key), MainActivity.LOGGED_OUT);
        viewModel.insertRestaurantWithUser(userId, restaurantName, cuisine, city, rating, visited);

        toastMaker("Added " + restaurantName);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    static Intent addRestaurantActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AddRestaurantActivity.class);
        return intent;
    }

}
