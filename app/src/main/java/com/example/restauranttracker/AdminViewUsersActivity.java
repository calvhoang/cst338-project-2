package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivityAdminViewUsersBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminViewUsersActivity extends BaseActivity {

    ActivityAdminViewUsersBinding binding;
    private AppRepository repository;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        displayAllUsers();

    }

    private void displayAllUsers() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        repository.getAllUsers().observe(this, users -> {
            if (users.isEmpty()) {
                Toast.makeText(this, "No Users", Toast.LENGTH_SHORT).show();
            } else {
                for (User user : users) {
                    sb.append(user.getUsername()).append("\n");
                    sb2.append(user.getPassword()).append("\n");
                }
                binding.allUsersTextView.setText(sb.toString());
                binding.allPasswordsTextView.setText(sb2.toString());
            }
        });
    }

    static Intent adminViewUsersActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminViewUsersActivity.class);
        return intent;
    }
}