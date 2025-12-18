package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivityAdminChangeUsernameBinding;

public class AdminChangeUsernameActivity extends BaseActivity {
    ActivityAdminChangeUsernameBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminChangeUsernameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Change User Username");
        }
        repository = AppRepository.getRepository(getApplication());

        binding.changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });
    }

    private void changeUsername() {
        String oldUsername = binding.oldUsernameEditText.getText().toString();
        String newUsername = binding.newUsernameEditText.getText().toString();
        if (oldUsername.isEmpty() || newUsername.isEmpty()) {
            Toast.makeText(this, "No Username Entered", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(oldUsername);
        userObserver.observe(this, user -> {
            if (user != null) {
                userObserver.removeObservers(this);
                LiveData<Boolean> booleanObserver = repository.usernameExists(newUsername);
                booleanObserver.observe(this, usernameExits -> {
                    if (usernameExits) {
                        Toast.makeText(this, String.format("%s is not available.", newUsername), Toast.LENGTH_SHORT).show();
                    } else {
                        booleanObserver.removeObservers(this);
                        user.setUsername(newUsername);
                        repository.updateUser(user);
                        Toast.makeText(this, String.format("%s changed to %s", oldUsername, newUsername), Toast.LENGTH_SHORT).show();
                        startActivity(AdminActivity.adminActivityIntentFactory(getApplicationContext()));
                    }
                });
            } else {
                Toast.makeText(this, String.format("%s does not exist.", oldUsername), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent adminChangeUsernameActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminChangeUsernameActivity.class);
        return intent;
    }
}
