package com.example.restauranttracker.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.restauranttracker.Database.AppDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = AppDatabase.USER_RESTAURANT_TABLE,
        foreignKeys = {
                @ForeignKey(
                        entity = Restaurant.class,
                        parentColumns = "id",
                        childColumns = "id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "restaurantId",
                        childColumns = "restaurantId",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class UserRestaurant {

    @PrimaryKey(autoGenerate = true)
    private int userRestaurantId;
    private int rating;
    private boolean hasBeenTo;
    private LocalDateTime date;

    public UserRestaurant(int userRestaurantId, int rating, boolean hasBeenTo) {
        this.userRestaurantId = userRestaurantId;
        this.rating = rating;
        this.hasBeenTo = hasBeenTo;
        date = LocalDateTime.now();
    }

    public int getUserRestaurantId() {
        return userRestaurantId;
    }

    public void setUserRestaurantId(int userRestaurantId) {
        this.userRestaurantId = userRestaurantId;
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
        return userRestaurantId == that.userRestaurantId && rating == that.rating && hasBeenTo == that.hasBeenTo && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRestaurantId, rating, hasBeenTo, date);
    }
}
