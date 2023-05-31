package com.example.craveapplication.searchByMeal.presenter;

import com.example.craveapplication.mealDetails.view.MealDetailsActivityInterface;
import com.example.craveapplication.model.MealByNameResponse;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.remoteSource.remoteAPI.RemoteSource;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.searchByMeal.view.SearchByMealFragmentInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByMealPresenter {
    RepoInterface repo ;
    SearchByMealFragmentInterface searchByMealFragmentInterface;


    public SearchByMealPresenter(SearchByMealFragmentInterface searchByMealFragmentInterface , RepoInterface repo){
            this.searchByMealFragmentInterface=searchByMealFragmentInterface;
            this.repo=repo;
    }

    public Single<MealByNameResponse> getMealsByName(String id) {
        return repo.getMealsByName(id);
    }
}
