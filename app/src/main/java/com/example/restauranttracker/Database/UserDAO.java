package com.example.restauranttracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restauranttracker.Database.entities.User;

import java.util.List;

// DAO interface for User entity
//represents the queries that can be performed on the User table/in the database.
@Dao
public interface UserDAO {
    //if a conflict occurs (e.g., inserting a user with an existing primary key), the existing record will be replaced with the new one.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    // Deletes a specific user from the database.
    @Delete
    void delete(User user);

    // Retrieves all users from the database, ordered by username.
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    // Deletes all records from the User table.
    @Query("DELETE from " + AppDatabase.USER_TABLE)
    void deleteAll();

    // Retrieves a user by their username.
    @Query("SELECT * from " + AppDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    // Retrieves a user by their user ID.
    @Query("SELECT * from " + AppDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);


}