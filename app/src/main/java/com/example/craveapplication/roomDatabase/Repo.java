package com.example.craveapplication.roomDatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.craveapplication.HomeActivity;
import com.example.craveapplication.mealDetails.presenter.MealsDetailsPresenter;
import com.example.craveapplication.mealDetails.presenter.NetworkDelegateMealDetails;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.model.MealByNameResponse;
import com.example.craveapplication.remoteSource.remoteAPI.NetworkDelegate;
import com.example.craveapplication.remoteSource.remoteAPI.RemoteSource;
import com.example.craveapplication.searchResult.presenter.NetworkDelegateResultIntefrace;
import com.example.craveapplication.search.presenter.NetworkDelegateSearchInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repo implements RepoInterface{
    private MealDao mealDao;
    RemoteSource remoteSource ;
    LocalSource localSource;
    private static Repo repo = null;

    private Context context;
    public Repo(Context context , LocalSource localSource , RemoteSource remoteSource){
        this.localSource = localSource;
        this.remoteSource =remoteSource;
        this.context =context;
    }

    public static synchronized Repo getInstance(LocalSource localSource , RemoteSource remoteSource ,Context context){
        if (repo == null){
            repo = new Repo(context , localSource , remoteSource);
        }
        return repo;
    }


    public void insertMeal(Meal meal) {
        localSource.insert(meal);    }

    public LiveData<List<Meal>> getMeal() {
       return localSource.getCachedMeals();    }

    @Override
    public LiveData<List<Meal>> getCachedMeals() {
        return localSource.getCachedMeals();    }
    public LiveData<List<Meal>> getRandomMeal() {
        return localSource.getCachedMeals();    }

    public void deleteMeal(Meal meal) {
        localSource.delete(meal);    }

    public void getMealsFromNetwork(NetworkDelegate networkDelegate) {
        remoteSource.getMealsFromNetwork(networkDelegate);
    }
    public void getRandomMealFromNetwork(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMealFromNetwork(networkDelegate);
    }

    public void getCategoriesFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getCategoriesFromNetwork(networkDelegate);
    }

    public void getAreasFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getAreasFromNetwork(networkDelegate);
    }

    public void getIngredientsFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        remoteSource.getIngredientsFromNetwork(networkDelegate);
    }

    @Override
    public void getResultFromNetwork(NetworkDelegateResultIntefrace networkDelegate , String typeName,String type) {
        remoteSource.getResultFromNetwork(networkDelegate ,  typeName, type);
    }

    @Override
    public void getMealDetailsFromNetwork(NetworkDelegateMealDetails networkDelegateMealDetails , String mealID) {
        remoteSource.getMealDetailsFromNetwork(networkDelegateMealDetails , mealID );
    }
    public void getUserMeals(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference mealsCollection = db.collection("meals");

        Query query = mealsCollection.whereEqualTo("email", HomeActivity.userEmail);
        System.out.println(userId);
        query.get().addOnCompleteListener(onCompleteListener);
    }

    public Single<MealByNameResponse> getMealsByName(String id) {
        return remoteSource.getMealsByName(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
