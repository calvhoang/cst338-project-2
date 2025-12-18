package com.example.restauranttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.databinding.ActivityAdminDeleteUserBinding;

public class AdminDeleteUserActivity extends BaseActivity {

    ActivityAdminDeleteUserBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDeleteUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }
    private void deleteUser(){
        String username = binding.usernameEditText.getText().toString();
        if(username.isEmpty()) {
            Toast.makeText(this, "Username should not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null) {
                repository.deleteUser(user);
                Toast.makeText(this, String.format("%s Deleted", username), Toast.LENGTH_SHORT).show();
                startActivity(AdminActivity.adminActivityIntentFactory(getApplicationContext()));
            }
            else{
                Toast.makeText(this, String.format("%s does not exist.", username), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent adminDeleteUserActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminDeleteUserActivity.class);
        return intent;
    }
}