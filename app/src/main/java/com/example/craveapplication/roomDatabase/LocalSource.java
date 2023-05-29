package com.example.craveapplication.roomDatabase;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface LocalSource {
    public void insert(Meal meal);

    public void delete(Meal meal);

    public LiveData<List<Meal>> getCachedMeals();
    public LiveData<List<Meal>> getRandomMeal();
}
