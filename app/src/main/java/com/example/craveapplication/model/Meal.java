
package com.example.craveapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@Entity(tableName = "meals")
public class Meal {
    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    private String mIdMeal;
    @SerializedName("strMeal")
    private String mStrMeal;
    @SerializedName("strMealThumb")
    private String mStrMealThumb;

    public String getIdMeal() {
        return mIdMeal;
    }

    public void setIdMeal(String idMeal) {
        mIdMeal = idMeal;
    }

    public String getStrMeal() {
        return mStrMeal;
    }

    public void setStrMeal(String strMeal) {
        mStrMeal = strMeal;
    }

    public String getStrMealThumb() {
        return mStrMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        mStrMealThumb = strMealThumb;
    }

}
