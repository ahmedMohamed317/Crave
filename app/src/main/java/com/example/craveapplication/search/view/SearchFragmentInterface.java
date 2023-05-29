package com.example.craveapplication.search.view;

import android.content.Context;

import com.example.craveapplication.model.Area;
import com.example.craveapplication.model.CategoryPojo;
import com.example.craveapplication.model.Ingredient;
import com.example.craveapplication.model.Meal;

import java.util.List;

public interface SearchFragmentInterface {

    public void showCategoryData(List<CategoryPojo> categoryPojos);
    public void showAreaData(List<Area> areas);
    public void showIngredientData(List<Ingredient> ingredients);
    public void clickOnArea(Context context, String areaName, String type);
    public void clickOnIngredients(Context context, String areaName, String type);
    public void clickOnCategory(Context context, String areaName, String type);
}
