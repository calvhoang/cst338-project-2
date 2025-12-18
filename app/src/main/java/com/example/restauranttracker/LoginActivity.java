package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivityLoginBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppViewModel viewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignUpActivity.SignUpIntentFactory(getApplicationContext()));
            }
        });

    }

    // Verifies the user credentials and logs them in if valid.
    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            toastMaker("Username and/or password should not be blank");
            return;
        }

        viewModel.login(username, password);
        viewModel.getLoggedInUser().observe(this, user -> {
            if (user == null) {
                toastMaker("Invalid username and/or password");
                return;
            }

            toastMaker("Login success!");
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}