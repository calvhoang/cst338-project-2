package com.example.restauranttracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;

@Dao
public interface UserRestaurantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserRestaurant... userRestaurant);

    @Delete
    void delete(UserRestaurant... userRestaurant);

    @Query("DELETE FROM " + AppDatabase.USER_RESTAURANT_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.USER_RESTAURANT_TABLE + " WHERE userId == :userId")
    LiveData<User> getRestaurantByUserId(int userId);
}
