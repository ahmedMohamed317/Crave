package com.example.craveapplication.favourite.view;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface FavouriteFragmentInterface {
    public void addFav(Meal meal);
    public void deleteFav(Meal meal);
    public LiveData<List<Meal>> getFav();

}
