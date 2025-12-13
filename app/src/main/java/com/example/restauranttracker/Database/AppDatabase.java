package com.example.restauranttracker.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.restauranttracker.Database.entities.Restaurant;
import com.example.restauranttracker.Database.entities.User;
import com.example.restauranttracker.Database.entities.UserRestaurant;
import com.example.restauranttracker.Database.typeConverters.LocalDateTypeConverter;
import com.example.restauranttracker.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Restaurant.class, User.class, UserRestaurant.class},
        version = 2,
        exportSchema = false)
@TypeConverters(LocalDateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Restaurant_Tracker_Database";
    public static final String RESTAURANT_TABLE = "restaurantTable";
    public static final String USER_TABLE = "userTable";
    public static final String USER_RESTAURANT_TABLE = "userRestaurantTable";

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDao();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract RestaurantDAO restaurantDAO();
    public abstract UserDAO userDao();
    public abstract UserRestaurantDAO userRestaurantDAO();
}
