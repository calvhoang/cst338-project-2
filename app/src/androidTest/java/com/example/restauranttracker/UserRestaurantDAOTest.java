package com.example.restauranttracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.RestaurantDAO;
import com.example.restauranttracker.Database.UserDAO;
import com.example.restauranttracker.Database.UserRestaurantDAO;
import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserRestaurantDAOTest {
    private UserRestaurantDAO userRestaurantDAO;
    private RestaurantDAO restaurantDAO;
    private UserDAO userDAO;
    private AppDatabase db;
    private UserRestaurant userRestaurant;
    private Restaurant restaurant;
    private User user;
    long restaurantId;
    private String name;
    private String cuisine;
    private String city;
    private int rating;
    private boolean visited;
    private static final int TEST_USER_ID = 1;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userRestaurantDAO = db.userRestaurantDAO();
        restaurantDAO = db.restaurantDAO();
        userDAO = db.userDao();

        name = "Restaurant";
        cuisine = "Cuisine";
        city = "City";
        rating = 5;
        visited = true;

        User user = new User("test1", "test1");
        userDAO.insert(user);

        restaurant = new Restaurant(name, cuisine, city);
        restaurantId = restaurantDAO.insert(restaurant);

        userRestaurant = new UserRestaurant(TEST_USER_ID, restaurantId, rating, visited);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertUserRestaurant() {
        userRestaurantDAO.insert(userRestaurant);

        UserRestaurant test = userRestaurantDAO.getUserRestaurantByIds(TEST_USER_ID, restaurantId);
        assertNotNull(test);

        assertEquals(rating, test.getRating());
        assertEquals(visited, test.getVisited());
    }

    @Test
    public void updateUserRestaurant() {
        userRestaurantDAO.insert(userRestaurant);

        userRestaurant.setRating(1);
        userRestaurant.setVisited(false);
        userRestaurantDAO.update(userRestaurant);

        UserRestaurant test = userRestaurantDAO.getUserRestaurantByIds(TEST_USER_ID, restaurantId);
        assertNotNull(test);

        assertEquals(1, test.getRating());
        assertFalse(userRestaurant.getVisited());
    }

    @Test
    public void deleteUserRestaurant() {
        userRestaurantDAO.insert(userRestaurant);

        UserRestaurant test = userRestaurantDAO.getUserRestaurantByIds(TEST_USER_ID, restaurantId);
        assertNotNull(test);

        userRestaurantDAO.delete(test);

        UserRestaurant deleted = userRestaurantDAO.getUserRestaurantByIds(TEST_USER_ID, restaurantId);
        assertNull(deleted);
    }
}
