package com.example.craveapplication.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.model.Meal;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.searchResult.view.SearchResultInterface;

import java.util.List;

public class FavouritePresenter {
    private RepoInterface repoInterface;
    private SearchResultInterface view;
    String typeName;
    String type;


    public FavouritePresenter( RepoInterface repoInterface ){
        this.repoInterface =repoInterface;

    }



    public void addFav(Meal meal) {

        repoInterface.insertMeal(meal);
    }

    public void deleteFav(Meal meal) {

        repoInterface.deleteMeal(meal);
    }
    public LiveData<List<Meal>> getFav() {

        return repoInterface.getCachedMeals();
    }





}

