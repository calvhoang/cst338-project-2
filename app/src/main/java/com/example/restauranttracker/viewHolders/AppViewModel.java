package com.example.restauranttracker.viewHolders;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.AppRepository;
import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;
import com.example.restauranttracker.MainActivity;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public long insertRestaurant(Restaurant restaurant) {
        return repository.insertRestaurant(restaurant);
    }
/*
    public LiveData<Restaurant> getRestaurantInfo(String restaurantName, String cuisine, String city) {
        return repository.getRestaurantInfo(restaurantName, cuisine, city);
    } */

    public void insertUserRestaurant(UserRestaurant userRestaurant) {
        repository.insertUserRestaurant(userRestaurant);
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


}
