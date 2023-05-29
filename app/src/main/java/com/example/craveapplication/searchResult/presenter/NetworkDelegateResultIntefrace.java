package com.example.craveapplication.searchResult.presenter;

import com.example.craveapplication.model.Meal;

import java.util.List;

public interface NetworkDelegateResultIntefrace {
    public void onSuccessResult(List<Meal> meals);
    public void onFailureResult(String error);
}
