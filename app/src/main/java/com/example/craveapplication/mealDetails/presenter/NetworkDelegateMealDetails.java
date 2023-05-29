package com.example.craveapplication.mealDetails.presenter;

import com.example.craveapplication.model.DetailMeal;
import com.example.craveapplication.model.Meal;

import java.util.List;

public interface NetworkDelegateMealDetails {

    public void onSuccessResult(DetailMeal detailMeal);
    public void onFailureResult(String error);

}
