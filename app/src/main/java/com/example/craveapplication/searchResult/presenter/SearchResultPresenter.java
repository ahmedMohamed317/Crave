package com.example.craveapplication.searchResult.presenter;

import com.example.craveapplication.model.Meal;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.searchResult.view.SearchResultInterface;

import java.util.List;

public class SearchResultPresenter implements NetworkDelegateResultIntefrace{


    private RepoInterface repoInterface;
    private SearchResultInterface view;
    String typeName;
    String type;


    public SearchResultPresenter(SearchResultInterface view, RepoInterface repoInterface , String typeName,String type){
        this.repoInterface =repoInterface;
        this.view =view;
        this.typeName = typeName ;
        this.type = type ;
    }

    public void getResults() {
        repoInterface.getResultFromNetwork(this,typeName , type);
    }


    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showResult(meals);
    }

    @Override
    public void onFailureResult(String error) {

    }
    public void addFav(Meal meal) {

        repoInterface.insertMeal(meal);
    }

}
