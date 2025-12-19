package com.example.restauranttracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.restauranttracker.Database.AppDatabase;
import com.example.restauranttracker.Database.RestaurantDAO;
import com.example.restauranttracker.Database.entities.Restaurant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RestaurantDAOTest {
    private RestaurantDAO restaurantDAO;
    private AppDatabase db;
    private Restaurant restaurant;
    private String name;
    private String cuisine;
    private String city;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        restaurantDAO = db.restaurantDAO();

        name = "Restaurant";
        cuisine = "Cuisine";
        city = "City";
        restaurant = new Restaurant(name, cuisine, city);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertRestaurant() {
        long restaurantId = restaurantDAO.insert(restaurant);

        Restaurant test = restaurantDAO.getRestaurantInfo(name, cuisine, city);
        assertNotNull(test);

        assertEquals(restaurantId, test.getRestaurantId());
        assertEquals(name, test.getName());
        assertEquals(cuisine, test.getCuisine());
        assertEquals(city, test.getCity());
    }

    @Test
    public void updateRestaurant() {
        long id = restaurantDAO.insert(restaurant);
        restaurant.setRestaurantId(id);

        name = "Pizza Hut";
        cuisine = "Italian-American";
        city = "San Jose";

        restaurant.setName(name);
        restaurant.setCuisine(cuisine);
        restaurant.setCity(city);

        restaurantDAO.update(restaurant);
        Restaurant test = restaurantDAO.getRestaurantInfo(name, cuisine, city);
        assertNotNull(test);

        assertEquals(name, test.getName());
        assertEquals(cuisine, test.getCuisine());
        assertEquals(city, test.getCity());
    }

    @Test
    public void deleteRestaurant() {
        restaurantDAO.insert(restaurant);

        Restaurant test = restaurantDAO.getRestaurantInfo(name, cuisine, city);
        assertNotNull(test);

        restaurantDAO.delete(test);

        Restaurant deleted = restaurantDAO.getRestaurantInfo(name, cuisine, city);
        assertNull(deleted);
    }
}