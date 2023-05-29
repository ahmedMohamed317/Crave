package com.example.craveapplication.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.craveapplication.R;
import com.example.craveapplication.home.presenter.HomeFragmentPresenter;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  implements HomeFragmentInterface {
    RecyclerView rvSuggestedMeal;
    RecyclerView rvRandomMeal;
    HomeFragmentPresenter homeFragmentPresenter;
    SuggestedMealAdapter adapter;
    List<Meal> suggestedMeals = new ArrayList<>();
    List<Meal> randomMeal = new ArrayList<>();
    RepoInterface repo ;
    RandomMealAdapter randomMealAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getContext()) , ApiClient.getInstance(),getContext());
        rvSuggestedMeal = view.findViewById(R.id.recycelrViewYouMightLikeHome);
        rvSuggestedMeal.setHasFixedSize(true);
        rvRandomMeal = view.findViewById(R.id.recyclerViewDishOfTheDay);
        rvRandomMeal.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvSuggestedMeal.setLayoutManager(layoutManager);
        rvRandomMeal.setLayoutManager(layoutManager2);
        adapter = new SuggestedMealAdapter(getContext(), suggestedMeals );
        randomMealAdapter = new RandomMealAdapter(getContext(), randomMeal );
        rvSuggestedMeal.setAdapter(adapter);
        rvRandomMeal.setAdapter(randomMealAdapter);
        homeFragmentPresenter = new HomeFragmentPresenter( this , repo);
        homeFragmentPresenter.getMeals();
        homeFragmentPresenter.getRandomMeal();
    }


    @Override
    public void showData(List<Meal> meals) {
        suggestedMeals=meals;
        adapter.setList(meals);
    }

    @Override
    public void addMeal(Meal product) {

    }
    @Override
    public void showRandomMealData(List<Meal> meals) {
        randomMeal=meals;
        randomMealAdapter.setList(meals);
    }
}