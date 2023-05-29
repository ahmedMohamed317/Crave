package com.example.craveapplication.search.presenter;

import com.example.craveapplication.model.Area;
import com.example.craveapplication.model.CategoryPojo;
import com.example.craveapplication.model.Ingredient;
import com.example.craveapplication.model.Meal;

import java.util.List;

public interface NetworkDelegateSearchInterface {

    void onSuccessResultCategories(List<CategoryPojo> category);

    void onFailureResultCatogeries(String error);

    void onSuccessResultAreas(List<Area> area);

    void onFailureResultAreas(String error);

    void onSuccessResultIng(List<Ingredient> ingredient);

    void onFailureResultIng(String error);
}
