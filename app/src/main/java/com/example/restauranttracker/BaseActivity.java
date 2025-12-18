package com.example.restauranttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.User;

public abstract class BaseActivity extends AppCompatActivity {
    protected AppRepository repository;
    protected int loggedInUserId = LOGGED_OUT;
    protected User user;

    protected static final String MAIN_ACTIVITY_USER_ID = "com.example.restaurant_tracker.MAIN_ACTIVITY_USER_ID";
    protected static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.restaurant_tracker.SAVED_INSTANCE_STATE_USERID_KEY";

    public static final int LOGGED_OUT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    protected void loginUser(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sp.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);


        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
            }
        });
    }
    // Update shared preference with logged in user id
    protected void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

private void showLogoutDialog() {
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
    final AlertDialog alertDialog = alertBuilder.create();

    alertBuilder.setMessage("Logout?");

    alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            logout();
        }
    });
    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            alertDialog.dismiss();
        }
    });
    alertBuilder.create().show();
}

    // Logs out the current user and navigates to the login screen
    protected void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
}
