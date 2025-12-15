package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminViewUsersActivity.adminViewUsersActivityIntentFactory(getApplicationContext()));
            }
        });
        binding.changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminChangeUsernameActivity.adminChangeUsernameActivityIntentFactory(getApplicationContext()));
            }
        });
        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminChangePasswordActivity.adminChangePasswordActivityIntentFactory(getApplicationContext()));
            }
        });

    }

    static Intent adminActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }
}
