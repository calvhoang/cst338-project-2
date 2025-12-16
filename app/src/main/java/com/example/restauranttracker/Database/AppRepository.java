package com.example.restauranttracker.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.RestaurantUserRestaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;
import com.example.restauranttracker.MainActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final RestaurantDAO restaurantDAO;
    private final UserRestaurantDAO userRestaurantDAO;
    private static AppRepository repository;

    // Singleton pattern to ensure only one instance of the repository exists.
    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDao();
        this.restaurantDAO = db.restaurantDAO();
        this.userRestaurantDAO = db.userRestaurantDAO();
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
    public void insertUser(User... user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }
    public LiveData<Boolean> usernameExists(String username){
        return userDAO.usernameExists(username);//(AppDatabase.USER_TABLE.contains(username));
    }

    // Method to get a user by username from the database.
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<Boolean> isUserAdmin(int userId) {
        return userDAO.isAdmin(userId);
    }
    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }
    public void deleteUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.delete(user);
        });
    }

    public long insertRestaurant(Restaurant restaurant) {
        return restaurantDAO.insert(restaurant);
    }

    public LiveData<Restaurant> getRestaurantInfoLiveData(String restaurantName, String cuisine, String city) {
        return restaurantDAO.getRestaurantInfoLiveData(restaurantName, cuisine, city);
    }

    public Restaurant getRestaurantInfo(String restaurantName, String cuisine, String city) {
        return restaurantDAO.getRestaurantInfo(restaurantName, cuisine, city);
    }

    public LiveData<List<RestaurantUserRestaurant>> getRestaurantsByUserId(int userId) {
        return restaurantDAO.getRestaurantsByUserId(userId);
    }

    public LiveData<RestaurantUserRestaurant> getRandomRestaurantByUserId(int userId) {
        return restaurantDAO.getRandomRestaurantByUserId(userId);
    }

    public void insertUserRestaurant(UserRestaurant userRestaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userRestaurantDAO.insert(userRestaurant);
        });
    }
}
