package com.example.craveapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailMealResponse {
    @SerializedName("meals")
    private List<DetailMeal> detailMeal;

    public DetailMealResponse() {
    }

    public List<DetailMeal> getMeals() {
        return detailMeal;
    }
}
