package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivitySignUpBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppViewModel viewModel;
    private User user;

    static Intent SignUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).hide();

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
            toastMaker("Username cannot be empty");
            return;
        }
        if (password.length() < 5) {
            toastMaker("Password must Be at least 5 characters long");
            return;
        }

        viewModel.signUp(username, password);
        viewModel.getLoggedInUser().observe(this, user -> {
            if (user == null) {
                toastMaker("Username is not available");
                return;
            }

            toastMaker("Account created");
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}