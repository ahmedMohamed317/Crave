package com.example.craveapplication.searchResult.view;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface SearchResultInterface {
    public void showResult(List<Meal> meals);
    public void addFav(Meal meal);



}
