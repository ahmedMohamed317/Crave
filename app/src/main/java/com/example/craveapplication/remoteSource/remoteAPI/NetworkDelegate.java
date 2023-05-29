package com.example.craveapplication.remoteSource.remoteAPI;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface NetworkDelegate {
    void onSuccessResult(List<Meal> meal);

    void onFailureResult(String error);

    void onSuccessResultRandomMeal(List<Meal> meal);

    void onFailureResultRandomMeal(String error);
}
