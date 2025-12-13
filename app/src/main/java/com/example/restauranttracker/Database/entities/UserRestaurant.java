package com.example.restauranttracker.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.restauranttracker.Database.AppDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = AppDatabase.USER_RESTAURANT_TABLE,
        primaryKeys = {"userId", "restaurantId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Restaurant.class,
                        parentColumns = "restaurantId",
                        childColumns = "restaurantId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = {@Index("restaurantId"), @Index("userId")}
        )
public class UserRestaurant {
    public int userId;
    public long restaurantId;
    private int rating;
    private boolean visited;
    private LocalDateTime date;

    public UserRestaurant(int userId, long restaurantId, int rating, boolean visited) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.visited = visited;
        date = LocalDateTime.now();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRestaurant that = (UserRestaurant) o;
        return rating == that.rating && visited == that.visited && userId == that.userId && restaurantId == that.restaurantId && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, visited, date, userId, restaurantId);
    }
}
