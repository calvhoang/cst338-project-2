package com.example.restauranttracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.RestaurantUserRestaurant;

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

    // Gets all restaurants by userId
    @Query("SELECT res.*, userRes.restaurantId, userRes.rating, userRes.visited, userRes.date FROM "
            + AppDatabase.RESTAURANT_TABLE + " res "
            + " INNER JOIN " + AppDatabase.USER_RESTAURANT_TABLE + " userRes ON res.restaurantId = userRes.restaurantId "
            + "WHERE userRes.userId = :userId")
    LiveData<List<RestaurantUserRestaurant>> getRestaurantsByUserId(int userId);

    // Gets a random restaurant by userId
    @Query("SELECT res.*, userRes.restaurantId, userRes.rating, userRes.visited, userRes.date FROM "
            + AppDatabase.RESTAURANT_TABLE + " res "
            + " INNER JOIN " + AppDatabase.USER_RESTAURANT_TABLE + " userRes ON res.restaurantId = userRes.restaurantId "
            + " WHERE userRes.userId = :userId ORDER BY RANDOM() LIMIT 1")
    RestaurantUserRestaurant getRandomRestaurantByUserId(int userId);

    @Query("DELETE FROM " + AppDatabase.RESTAURANT_TABLE)
    void deleteAll();
}
