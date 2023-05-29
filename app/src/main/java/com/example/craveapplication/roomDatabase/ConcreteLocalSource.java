package com.example.craveapplication.roomDatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.model.Meal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource {

    private MealDao mealDAO;
    private static ConcreteLocalSource concreteLocalSource = null;

    private ConcreteLocalSource(Context context) {
        mealDAO = AppDatabase.getInstance(context.getApplicationContext()).mealDao();
    }

    public static synchronized ConcreteLocalSource getInstance(Context context) {

        if (concreteLocalSource == null) {
            concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }

    @Override
    public void insert(Meal meal) {
        new Thread() {
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void delete(Meal meal) {
        new Thread() {
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public LiveData<List<Meal>> getCachedMeals() {

        return mealDAO.getMeal();
    }

    public LiveData<List<Meal>> getRandomMeal() {
        return mealDAO.getMeal();
    }


}
