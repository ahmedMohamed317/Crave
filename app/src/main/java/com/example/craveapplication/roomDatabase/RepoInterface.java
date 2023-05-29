package com.example.craveapplication.roomDatabase;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.mealDetails.presenter.MealsDetailsPresenter;
import com.example.craveapplication.mealDetails.presenter.NetworkDelegateMealDetails;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.NetworkDelegate;
import com.example.craveapplication.searchResult.presenter.NetworkDelegateResultIntefrace;
import com.example.craveapplication.search.presenter.NetworkDelegateSearchInterface;


import java.util.List;

public interface RepoInterface {
    public LiveData<List<Meal>> getCachedMeals();

    public void deleteMeal(Meal meal);

    public void insertMeal(Meal meal);

    public void getMealsFromNetwork(NetworkDelegate networkDelegate);
    public void getRandomMealFromNetwork(NetworkDelegate networkDelegate);
    public void getCategoriesFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getAreasFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getIngredientsFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getResultFromNetwork(NetworkDelegateResultIntefrace networkDelegate , String typeName,String type);

    public void getMealDetailsFromNetwork(NetworkDelegateMealDetails networkDelegateMealDetails , String mealID);
}
