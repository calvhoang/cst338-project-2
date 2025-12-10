package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;

import com.example.restauranttracker.databinding.ActivitySignUpBinding;

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

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifyValidSignUpCredentials();
            }
        });

    }

    private void verifyValidSignUpCredentials(){
        String username = binding.userNameSignUpEditText.getText().toString();

        if(username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(repository.userNameExists(username)){
            Toast.makeText(this, "Username Not Available", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = binding.passwordSignUpEditText.getText().toString();
        if(password.length() < 5){
            Toast.makeText(this, "Password must Be at least 5 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        repository.insertUser(new User(username, password));
        //Navigate to menu page
        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), 1));
    }


}