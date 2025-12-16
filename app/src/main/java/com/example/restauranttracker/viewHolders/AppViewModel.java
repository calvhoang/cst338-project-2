package com.example.restauranttracker.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.RestaurantUserRestaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;

import java.util.List;
import java.util.function.Consumer;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<RestaurantUserRestaurant>> getRestaurantsByUserId(int userId) {
        return repository.getRestaurantsByUserId(userId);
    }

    public void insertRestaurantWithUser(int userId, String restaurantName, String cuisine, String city, int rating, boolean visited) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Restaurant restaurant = repository.getRestaurantInfo(restaurantName, cuisine, city);
            long restaurantId;

            if (restaurant == null) {
                Restaurant newRestaurant = new Restaurant(restaurantName, cuisine, city);
                restaurantId = repository.insertRestaurant(newRestaurant);
            } else {
                restaurantId = restaurant.getRestaurantId();
            }

            UserRestaurant userRestaurant = new UserRestaurant(userId, restaurantId, rating, visited);
            repository.insertUserRestaurant(userRestaurant);
        });
    }

    // Consumer used to pass method as an argument to another method, passing a random restaurant without using LiveData
    public void getRandomRestaurantByUserId(int userId, Consumer<RestaurantUserRestaurant> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            RestaurantUserRestaurant random = repository.getRandomRestaurantByUserId(userId);
            callback.accept(random);
        });
    }

    public void getUserByUsername(String username, Consumer<User> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = repository.getUserByUsername(username);
            callback.accept(user);
        });
    }

    public void usernameExists(String username, Consumer<Boolean> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Boolean exists = repository.usernameExistsSync(username);
            callback.accept(exists);
        });
    }

    public void login(String username, String password, Consumer<User> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = repository.getUserByUsername(username);

            if (user == null || !user.getPassword().equals(password)) {
                callback.accept(null);
                return;
            }

            callback.accept(user);
        });
    }

    public void signUp(String username, String password, Consumer<User> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Boolean exists = repository.usernameExistsSync(username);
            if (exists) {
                callback.accept(null);
                return;
            }
            User user = new User(username, password);
            repository.insertUser(user);
            callback.accept(user);
        });
    }

}
