package com.example.craveapplication.search.presenter;

import com.example.craveapplication.home.view.HomeFragmentInterface;
import com.example.craveapplication.model.Area;
import com.example.craveapplication.model.CategoryPojo;
import com.example.craveapplication.model.Ingredient;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.NetworkDelegate;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.search.view.SearchFragmentInterface;

import java.util.List;

public class SearchFragmentPresenter implements NetworkDelegateSearchInterface {

    private RepoInterface repoInterface;
    private SearchFragmentInterface view;


    public SearchFragmentPresenter(SearchFragmentInterface view, RepoInterface repoInterface){
        this.repoInterface =repoInterface;
        this.view =view;
    }


    public void getAreas() {
        repoInterface.getAreasFromNetwork(this);
    }

    public void getCategories() {
        repoInterface.getCategoriesFromNetwork(this);
    }
    public void getIngredients() {
        repoInterface.getIngredientsFromNetwork(this);
    }





    @Override
    public void onSuccessResultCategories(List<CategoryPojo> category) {
        view.showCategoryData(category);
    }

    @Override
    public void onFailureResultCatogeries(String error) {

    }

    @Override
    public void onSuccessResultAreas(List<Area> area) {
        view.showAreaData(area);
    }

    @Override
    public void onFailureResultAreas(String error) {

    }

    @Override
    public void onSuccessResultIng(List<Ingredient> ingredient) {
        view.showIngredientData(ingredient);
    }

    @Override
    public void onFailureResultIng(String error) {

    }
}
