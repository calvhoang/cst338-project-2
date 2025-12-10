package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.databinding.ActivityLoginBinding;
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

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAG, userId);
        return intent;
    }
}