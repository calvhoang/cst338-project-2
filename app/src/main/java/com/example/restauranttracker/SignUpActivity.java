package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;

import com.example.restauranttracker.databinding.ActivitySignUpBinding;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppRepository repository;

    static Intent SignUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifyValidSignUpCredentials();
            }
        });

    }

    private void verifyValidSignUpCredentials() {
        String username = binding.userNameSignUpEditText.getText().toString();
        String password = binding.passwordSignUpEditText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 5) {
            Toast.makeText(this, "Password must Be at least 5 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        repository.usernameExists(username).observe(this, usernameExits -> {
            if (usernameExits) {
                Toast.makeText(this, "Username Not Available", Toast.LENGTH_SHORT).show();
            }
            else {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                LiveData<User>userObserver = repository.getUserByUserName(username);
                userObserver.observe(this, user -> {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                });
            }
        });
    }
}