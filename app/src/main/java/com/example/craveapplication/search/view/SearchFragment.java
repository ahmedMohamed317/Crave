package com.example.craveapplication.search.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.craveapplication.R;
import com.example.craveapplication.model.Area;
import com.example.craveapplication.model.CategoryPojo;
import com.example.craveapplication.model.Ingredient;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.search.presenter.SearchFragmentPresenter;
import com.example.craveapplication.searchResult.view.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchFragmentInterface {

    List<Area> areaList = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();
    RepoInterface repo ;
    List<CategoryPojo> categoryList= new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecycleView;
    AreaAdapter areaAdapter;
    RecyclerView areaRecycleView;
    RecyclerView ingRecycleView;
    IngredientsAdapter ingredientAdapter;
    SearchFragmentPresenter searchPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getContext()) , ApiClient.getInstance(),getContext());
        categoryRecycleView = view.findViewById(R.id.searchRecycleView);
        categoryRecycleView.setHasFixedSize(true);
        areaRecycleView = view.findViewById(R.id.areaRecycleView);
        areaRecycleView.setHasFixedSize(true);
        ingRecycleView = view.findViewById(R.id.ingRecycleView);
        ingRecycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity().getApplicationContext(), 4);
        categoryRecycleView.setLayoutManager(layoutManager);
        areaRecycleView.setLayoutManager(layoutManager2);
        ingRecycleView.setLayoutManager(layoutManager3);
        categoryAdapter = new CategoryAdapter(getContext(), categoryList , this );
        areaAdapter = new AreaAdapter(getContext(), areaList, this );
        ingredientAdapter = new IngredientsAdapter(getContext(), ingredientList , this);
        categoryRecycleView.setAdapter(categoryAdapter);
       ingRecycleView.setAdapter(ingredientAdapter);
        areaRecycleView.setAdapter(areaAdapter);
        searchPresenter = new SearchFragmentPresenter( this , repo);
        searchPresenter.getAreas();
        searchPresenter.getCategories();
        searchPresenter.getIngredients();
        }




    @Override
    public void showCategoryData(List<CategoryPojo> categoryPojos) {
        categoryList=categoryPojos;
        categoryAdapter.setCategoryItems(categoryPojos);
    }

    @Override
    public void showAreaData(List<Area> areas) {
        areaList=areas;
        areaAdapter.setAreas(areas);

    }

    @Override
    public void showIngredientData(List<Ingredient> ingredients) {
        ingredientList=ingredients;
        ingredientAdapter.setIngredientsList(ingredients);

    }

    @Override
    public void clickOnArea(Context context, String areaName, String type) {
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("myData", areaName);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void clickOnIngredients(Context context, String ingName, String type) {
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("myData", ingName);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void clickOnCategory(Context context, String categoryName, String type) {
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("myData", categoryName);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}