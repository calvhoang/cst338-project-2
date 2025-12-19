package com.example.restauranttracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    private User testUser;
    private String testUsername;
    private String testPassword;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();

        testUsername = "Calvin";
        testPassword = "calvin123";
        testUser = new User(testUsername, testPassword);
    }

    @After
    public void closeDb() {
        db.close();
    }

    // Insert user and get user by username test
    @Test
    public void insertUser() {
        userDao.insert(testUser);

        User test = userDao.getUserByUsername(testUsername);

        assertEquals(testUsername, test.getUsername());
        assertEquals(testPassword, test.getPassword());
    }

    @Test
    public void updateUser() {
        long id = userDao.insert(testUser);
        testUser.setId((int) id);

        testUser.setUsername("Update");
        testUser.setPassword("update123");
        testUser.setAdmin(true);

        userDao.updateUser(testUser);

        User test = userDao.getUserByUsername("Update");

        assertNotNull(test);
        assertEquals("Update", test.getUsername());
        assertEquals("update123", test.getPassword());
        assertTrue(test.isAdmin());
    }

    @Test
    public void deleteUser() {
        userDao.insert(testUser);

        User test = userDao.getUserByUsername(testUsername);
        assertNotNull(test);

        userDao.delete(test);

        User deleted = userDao.getUserByUsername(testUsername);
        assertNull(deleted);
    }
}
