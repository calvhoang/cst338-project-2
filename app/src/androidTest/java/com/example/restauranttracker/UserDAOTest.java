package com.example.restauranttracker;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.UserDAO;
import com.example.restauranttracker.Database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserDAOTest {
    private UserDAO userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    // Insert user and get user by username test
    @Test
    public void insertUser_getUserByUserId() {
        User user = new User("david", "david123");
        userDao.insert(user);
        User test = userDao.getUserByUsername("david");
        assertEquals("david", test.getUsername());
    }

    @Test
    public void updateUser() {

    }

    @Test
    public void deleteUser() {

    }
}
