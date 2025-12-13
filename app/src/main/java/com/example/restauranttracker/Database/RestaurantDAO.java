package com.example.restauranttracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restauranttracker.Database.entities.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Restaurant restaurant);

    @Delete
    void delete(Restaurant... restaurant);

    @Query("SELECT * FROM " + AppDatabase.RESTAURANT_TABLE)
    LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM " + AppDatabase.RESTAURANT_TABLE + " WHERE name == :name AND cuisine == :cuisine AND city == :city LIMIT 1")
    Restaurant getRestaurantInfo(String name, String cuisine, String city);

    @Query("SELECT * FROM " + AppDatabase.RESTAURANT_TABLE + " WHERE name == :name AND cuisine == :cuisine AND city == :city")
    LiveData<Restaurant> getRestaurantInfoLiveData(String name, String cuisine, String city);

    @Query("DELETE FROM " + AppDatabase.RESTAURANT_TABLE)
    void deleteAll();
}
