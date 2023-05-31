package com.example.craveapplication.remoteSource.remoteAPI;

import com.example.craveapplication.mealDetails.presenter.NetworkDelegateMealDetails;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.model.MealByNameResponse;
import com.example.craveapplication.searchByMeal.presenter.NetworkDelegateSearchByName;
import com.example.craveapplication.searchResult.presenter.NetworkDelegateResultIntefrace;
import com.example.craveapplication.search.presenter.NetworkDelegateSearchInterface;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface RemoteSource {
    public void getMealsFromNetwork(NetworkDelegate networkDelegate);
    public void getRandomMealFromNetwork(NetworkDelegate networkDelegate);
    public void getCategoriesFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getAreasFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getIngredientsFromNetwork(NetworkDelegateSearchInterface networkDelegate);
    public void getResultFromNetwork(NetworkDelegateResultIntefrace networkDelegate , String typeName,String type);
    public void getMealDetailsFromNetwork(NetworkDelegateMealDetails networkDelegateMealDetails , String mealID);
    public Single<MealByNameResponse> getMealsByName(String name);

}
