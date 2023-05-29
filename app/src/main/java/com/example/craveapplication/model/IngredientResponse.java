package com.example.craveapplication.model;

import java.util.List;

public class IngredientResponse {

    private List<Ingredient> meals;

    public IngredientResponse() {
    }

    public List<Ingredient> getIngredients() {
        return meals;
    }

    public void setIngredients(List<Ingredient> meals) {
        this.meals = meals;
    }
}
