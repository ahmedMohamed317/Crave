package com.example.craveapplication.searchByMeal.presenter;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface NetworkDelegateSearchByName {


        public void onSuccessResult(List<Meal> meals);
        public void onFailureResult(String error);
    }


