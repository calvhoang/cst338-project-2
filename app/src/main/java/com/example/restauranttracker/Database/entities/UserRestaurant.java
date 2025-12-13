package com.example.restauranttracker.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
                )
        })
public class UserRestaurant {
    public int userId;
    public int restaurantId;
    private int rating;
    private boolean hasBeenTo;
    private LocalDateTime date;

    public UserRestaurant(int userId, int restaurantId, int rating, boolean hasBeenTo, LocalDateTime date) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.hasBeenTo = hasBeenTo;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isHasBeenTo() {
        return hasBeenTo;
    }

    public void setHasBeenTo(boolean hasBeenTo) {
        this.hasBeenTo = hasBeenTo;
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
        return rating == that.rating && hasBeenTo == that.hasBeenTo && userId == that.userId && restaurantId == that.restaurantId && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, hasBeenTo, date, userId, restaurantId);
    }
}
