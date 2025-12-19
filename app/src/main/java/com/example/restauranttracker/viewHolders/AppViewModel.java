package com.example.restauranttracker.viewHolders;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restauranttracker.BaseActivity;
import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.RestaurantUserRestaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;
import com.example.restauranttracker.R;

import java.util.List;
import java.util.function.Consumer;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository repository;
    private final SharedPreferences sp;
    private final Application app;

    // Writable LiveData for logged in user info
    private final MutableLiveData<User> loggedInUser = new MutableLiveData<>();

    public AppViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
        repository = AppRepository.getRepository(application);
        sp = application.getSharedPreferences(application.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
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

    public void login(String username, String password) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = repository.getUserByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                loggedInUser.postValue(null);
                return;
            }

            sp.edit().putInt(app.getString(R.string.preference_userId_key), user.getId()).apply();
            loggedInUser.postValue(user);
        });
    }

    public void signUp(String username, String password) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (repository.usernameExistsSync(username)) {
                loggedInUser.postValue(null);
                return;
            }

            User user = new User(username, password);
            repository.insertUser(user);

            User insertedUser = repository.getUserByUsername(username);

            sp.edit().putInt(app.getString(R.string.preference_userId_key), insertedUser.getId()).apply();
            loggedInUser.postValue(insertedUser);
        });
    }

    public void logout() {
        sp.edit().putInt(app.getString(R.string.preference_userId_key), BaseActivity.LOGGED_OUT).apply();
        loggedInUser.setValue(null);
    }

    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

}
