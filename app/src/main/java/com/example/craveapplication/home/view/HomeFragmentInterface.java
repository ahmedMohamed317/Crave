package com.example.craveapplication.home.view;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface HomeFragmentInterface {
    public void showData(List<Meal> meals);
    public void showRandomMealData(List<Meal> meals);
    public void addMeal(Meal meal);
}

