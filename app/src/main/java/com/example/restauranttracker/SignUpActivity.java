package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivitySignUpBinding;
import com.example.restauranttracker.viewHolders.AppViewModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppRepository repository;
    AppViewModel viewModel;

    static Intent SignUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
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
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 5) {
            Toast.makeText(this, "Password must Be at least 5 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
        repository.usernameExists(username).observe(this, usernameExists -> {
            if (usernameExists == null) {
                Toast.makeText(this, "usernameExists == null", Toast.LENGTH_SHORT).show();
                return;
            }
            if (usernameExists) {
                Toast.makeText(this, "Username Not Available", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                LiveData<User> userObserver = repository.getUserByUserName(username);
                userObserver.observe(this, user -> {
                    if (user == null) {
                        Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                });
            }
        });*/

        viewModel.signUp(username, password, user -> {
            runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(SignUpActivity.this, "Username Not Available", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                //finish();
            });

        });
    }
}