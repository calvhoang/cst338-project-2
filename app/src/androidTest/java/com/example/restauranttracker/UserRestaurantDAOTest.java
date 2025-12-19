package com.example.restauranttracker;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.UserRestaurantDAO;
import com.example.restauranttracker.Database.entities.UserRestaurant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserRestaurantDAOTest {
    private UserRestaurantDAO userRestaurantDAO;
    private AppDatabase db;
    private UserRestaurant userRestaurant;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userRestaurantDAO = db.userRestaurantDAO();


    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertUserRestaurant() {

    }

    @Test
    public void updateUserRestaurant() {

    }

    @Test
    public void deleteUserRestaurant() {

    }
}
