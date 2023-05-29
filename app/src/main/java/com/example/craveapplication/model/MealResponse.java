
package com.example.craveapplication.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MealResponse {

    @SerializedName("meals")
    private List<Meal> mMeals;

    public List<Meal> getMeals() {
        return mMeals;
    }

    public void setMeals(List<Meal> meals) {
        mMeals = meals;
    }

}
