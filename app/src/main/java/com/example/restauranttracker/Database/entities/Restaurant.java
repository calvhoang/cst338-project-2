package com.example.restauranttracker.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.restauranttracker.Database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.RESTAURANT_TABLE)
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    private int restaurantId;
    private String name;
    private String cuisine;

    private String city;

    public Restaurant(int restaurantId, String name, String cuisine) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.cuisine = cuisine;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return restaurantId == that.restaurantId && Objects.equals(name, that.name) && Objects.equals(cuisine, that.cuisine) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, name, cuisine, city);
    }
}
