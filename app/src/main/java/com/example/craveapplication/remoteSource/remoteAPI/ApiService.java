package com.example.craveapplication.remoteSource.remoteAPI;

import com.example.craveapplication.model.AreaResponse;
import com.example.craveapplication.model.CategoryResponse;
import com.example.craveapplication.model.DetailMealResponse;
import com.example.craveapplication.model.IngredientResponse;
import com.example.craveapplication.model.MealByNameResponse;
import com.example.craveapplication.model.MealResponse;
import com.example.craveapplication.model.RandomMeal;
import com.example.craveapplication.model.SuggestedMeal;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
      @GET("filter.php?a=Canadian")
     Call<SuggestedMeal> getMeals();

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("random.php")
    Call<RandomMeal> getRandomMeals();

    @GET("list.php?a=list")
    Call<AreaResponse> getArea();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredient();

    @GET("lookup.php")
    Call<DetailMealResponse> getDetailMeal(@Query("i") String id);

    @GET("search.php")
    Single<MealByNameResponse> getMealsByName(@Query("s") String name);



}