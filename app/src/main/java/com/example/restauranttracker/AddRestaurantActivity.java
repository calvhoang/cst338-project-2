package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.RestaurantDAO;
import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;
import com.example.restauranttracker.databinding.ActivityAddRestaurantBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;

public class AddRestaurantActivity extends AppCompatActivity {

    ActivityAddRestaurantBinding binding;
    AppRepository repository;
    AppViewModel viewModel;

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

        viewModel =  new ViewModelProvider(this).get(AppViewModel.class);

        binding.addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                Log.i("Calvin", "User input success");
                insertRestaurantInfo();
                Log.i("Calvin", "Insert restaurant info success");
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
    private void insertRestaurantInfo() {
        if (restaurantName.isEmpty()) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(getString(R.string.preference_userId_key), MainActivity.LOGGED_OUT);
        Log.i("Calvin", "User id success: " + userId);
        viewModel.insertRestaurantWithUser(userId, restaurantName, cuisine, city, rating, visited);
        Log.i("Calvin", "Insert Restaurant with User success");
    }

    static Intent addRestaurantActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AddRestaurantActivity.class);
        return intent;
    }

}
