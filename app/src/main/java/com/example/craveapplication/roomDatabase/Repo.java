package com.example.craveapplication.roomDatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.mealDetails.presenter.MealsDetailsPresenter;
import com.example.craveapplication.mealDetails.presenter.NetworkDelegateMealDetails;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.NetworkDelegate;
import com.example.craveapplication.remoteSource.remoteAPI.RemoteSource;
import com.example.craveapplication.searchResult.presenter.NetworkDelegateResultIntefrace;
import com.example.craveapplication.search.presenter.NetworkDelegateSearchInterface;


import java.util.List;

public class Repo implements RepoInterface{
    private MealDao mealDao;
    RemoteSource remoteSource ;
    LocalSource localSource;
    private static Repo repo = null;

    private Context context;
    public Repo(Context context , LocalSource localSource , RemoteSource remoteSource){
        this.localSource = localSource;
        this.remoteSource =remoteSource;
        this.context =context;
    }

    public static synchronized Repo getInstance(LocalSource localSource , RemoteSource remoteSource ,Context context){
        if (repo == null){
            repo = new Repo(context , localSource , remoteSource);
        }
        return repo;
    }


    public void insertMeal(Meal meal) {
        localSource.insert(meal);    }

    public LiveData<List<Meal>> getMeal() {
       return localSource.getCachedMeals();    }

    @Override
    public LiveData<List<Meal>> getCachedMeals() {
        return localSource.getCachedMeals();    }
    public LiveData<List<Meal>> getRandomMeal() {
        return localSource.getCachedMeals();    }

    public void deleteMeal(Meal meal) {
        localSource.delete(meal);    }

    public void getMealsFromNetwork(NetworkDelegate networkDelegate) {
        remoteSource.getMealsFromNetwork(networkDelegate);
    }
    public void getRandomMealFromNetwork(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMealFromNetwork(networkDelegate);
    }

    public void getCategoriesFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getCategoriesFromNetwork(networkDelegate);
    }

    public void getAreasFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getAreasFromNetwork(networkDelegate);
    }

    public void getIngredientsFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getIngredientsFromNetwork(networkDelegate);
    }

    @Override
    public void getResultFromNetwork(NetworkDelegateResultIntefrace networkDelegate , String typeName,String type) {
        remoteSource.getResultFromNetwork(networkDelegate ,  typeName, type);
    }

    @Override
    public void getMealDetailsFromNetwork(NetworkDelegateMealDetails networkDelegateMealDetails , String mealID) {
        remoteSource.getMealDetailsFromNetwork(networkDelegateMealDetails , mealID );
    }


}
