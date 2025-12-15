package com.example.restauranttracker.Database;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * POJO class for joined tables Restaurant and UserRestaurant
 */
public class RestaurantUserRestaurantJoin {
    public long restaurantId;
    public String name;
    public String cuisine;
    public String city;

    public int rating;
    public boolean visited;
    public LocalDateTime date;

    @NonNull
    @Override
    public String toString() {
        return String.format(
                name + "\n" +
                        "Cuisine: " + cuisine + "\n" +
                        "City: " + city + "\n" +
                        "Rating: " + rating + "\n" +
                        "Visited: " + visited + "\n" +
                        "Date: " + date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantUserRestaurantJoin that = (RestaurantUserRestaurantJoin) o;
        return restaurantId == that.restaurantId && Objects.equals(name, that.name) && Objects.equals(cuisine, that.cuisine) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, name, cuisine, city);
    }
}
