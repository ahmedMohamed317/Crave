package com.example.craveapplication.mealDetails.presenter;

import com.example.craveapplication.mealDetails.view.MealDetailsActivityInterface;
import com.example.craveapplication.model.DetailMeal;
import com.example.craveapplication.roomDatabase.RepoInterface;

import java.util.List;

public class MealsDetailsPresenter implements  NetworkDelegateMealDetails{
    private RepoInterface repoInterface;
    private MealDetailsActivityInterface view;



    public MealsDetailsPresenter(MealDetailsActivityInterface view, RepoInterface repoInterface ){
        this.repoInterface =repoInterface;
        this.view =view;

    }


    @Override
    public void onSuccessResult(DetailMeal detailMeal) {
        view.showData(detailMeal);
    }

    @Override
    public void onFailureResult(String error) {

    }
    public void getMealDetails(String mealID) {
        repoInterface.getMealDetailsFromNetwork(this , mealID);
    }
}

