package com.example.craveapplication.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.craveapplication.model.Meal;

@Database(entities = Meal.class, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract MealDao mealDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                            AppDatabase.class,
                            "meals Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
