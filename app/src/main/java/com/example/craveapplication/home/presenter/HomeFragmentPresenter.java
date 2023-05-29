package com.example.craveapplication.home.presenter;

import com.example.craveapplication.home.view.HomeFragmentInterface;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.NetworkDelegate;
import com.example.craveapplication.roomDatabase.RepoInterface;

import java.util.List;

public class HomeFragmentPresenter implements NetworkDelegate {

    private RepoInterface repoInterface;
    private HomeFragmentInterface view;


    public HomeFragmentPresenter(HomeFragmentInterface view, RepoInterface repoInterface){
        this.repoInterface =repoInterface;
        this.view =view;
    }

    public void getMeals() {
        repoInterface.getMealsFromNetwork(this);
    }
    public void getRandomMeal() {
        repoInterface.getRandomMealFromNetwork(this);
    }


    public void addMeal(Meal meal) {
        repoInterface.insertMeal(meal);
    }

    @Override
    public void onSuccessResult(List<Meal> meal) {
        view.showData(meal);

    }

    @Override
    public void onFailureResult(String error) {

    }

    @Override
    public void onSuccessResultRandomMeal(List<Meal> meal) {
        view.showRandomMealData(meal);
    }

    @Override
    public void onFailureResultRandomMeal(String error) {

    }
}
