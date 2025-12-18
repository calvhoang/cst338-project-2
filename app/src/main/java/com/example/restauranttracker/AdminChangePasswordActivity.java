package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivityAdminChangePasswordBinding;

public class AdminChangePasswordActivity extends BaseActivity {

    ActivityAdminChangePasswordBinding binding;
    private AppRepository repository;

    EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        binding.findUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findUser();
            }
        });

    }

    private void changePassword() {
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        String newPassword = binding.newPasswordEditText.getText().toString();
        if (newPassword.length() < 5) {
            Toast.makeText(this, "Password must Be at least 5 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(usernameEditText.getText().toString());
        userObserver.observe(this, user -> {
            if (user != null) {
                userObserver.removeObservers(this);
                user.setPassword(newPassword);
                repository.updateUser(user);
                Toast.makeText(this, String.format("User %s password changed to %s", user.getUsername(), newPassword), Toast.LENGTH_SHORT).show();
                startActivity(AdminActivity.adminActivityIntentFactory(getApplicationContext()));
            } else {
                Toast.makeText(this, String.format("%s does not exist.", usernameEditText.getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void findUser() {
        String username = binding.usernameEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username should not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                binding.currentPasswordTextView.setText(user.getPassword());
            } else {
                Toast.makeText(this, String.format("%s does not exist.", username), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent adminChangePasswordActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminChangePasswordActivity.class);
        return intent;
    }

}
