package com.example.restauranttracker.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private static AppRepository repository;

    // Singleton pattern to ensure only one instance of the repository exists.
    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDao();
    }

    // Method to get the singleton instance of the repository.
    public static AppRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting AppRepository, thread error.");
        }
        return null;
    }

    // Method to insert a user into the database.
    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }
    public Boolean userNameExists(String username){
        return (AppDatabase.USER_TABLE.contains(username));
    }

    // Method to get a user by username from the database.
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}
