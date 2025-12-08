package com.example.restauranttracker.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;

@Database(
        entities = {Restaurant.class, User.class, UserRestaurant.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Restaurant_Tracker_Database";
    public static final String RESTAURANT_TABLE = "restaurantTable";
    public static final String USER_TABLE = "userTable";
    public static final String USER_RESTAURANT_TABLE = "userRestaurantTable";



    public abstract RestaurantDAO restaurantDAO();
    public abstract UserDAO userDao();
    public abstract UserRestaurantDAO userRestaurantDAO();
}
