package com.example.craveapplication.remoteSource.remoteAPI;

import com.example.craveapplication.mealDetails.presenter.NetworkDelegateMealDetails;
import com.example.craveapplication.model.AreaResponse;
import com.example.craveapplication.model.CategoryResponse;
import com.example.craveapplication.model.DetailMealResponse;
import com.example.craveapplication.model.IngredientResponse;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.model.MealResponse;
import com.example.craveapplication.model.RandomMeal;
import com.example.craveapplication.model.SuggestedMeal;
import com.example.craveapplication.searchResult.presenter.NetworkDelegateResultIntefrace;
import com.example.craveapplication.search.presenter.NetworkDelegateSearchInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements RemoteSource{
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static ApiService retrofit;
    private static ApiClient instance = null;
    List<Meal> meal;
    Call<MealResponse> call;

    private ApiClient() {
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }


    public void getMeals(NetworkDelegate network) {
        retrofit.getMeals().enqueue(new Callback<SuggestedMeal>() {
            @Override
            public void onResponse(Call<SuggestedMeal> call, Response<SuggestedMeal> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<SuggestedMeal> call, Throwable t) {
                network.onFailureResult(t.getMessage());
            }
        });
    }

    public void getRandomMeal(NetworkDelegate network) {
        retrofit.getRandomMeals().enqueue(new Callback<RandomMeal>() {
            @Override
            public void onResponse(Call<RandomMeal> call, Response<RandomMeal> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResultRandomMeal(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<RandomMeal> call, Throwable t) {
                network.onFailureResultRandomMeal(t.getMessage());
            }
        });
    }

    @Override
    public void getMealsFromNetwork(NetworkDelegate networkDelegate) {
        ApiClient.getInstance().getMeals(networkDelegate);

    }

    @Override
    public void getRandomMealFromNetwork(NetworkDelegate networkDelegate) {
        ApiClient.getInstance().getRandomMeal(networkDelegate);
    }

    @Override
    public void getCategoriesFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        ApiClient.getInstance().getCategories(networkDelegate);
    }

    @Override
    public void getAreasFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        ApiClient.getInstance().getAreas(networkDelegate);
    }

    @Override
    public void getIngredientsFromNetwork(NetworkDelegateSearchInterface networkDelegate) {
        ApiClient.getInstance().getIngredients(networkDelegate);
    }

    @Override
    public void getResultFromNetwork(NetworkDelegateResultIntefrace networkDelegate , String typeName,String type) {
        ApiClient.getInstance().searchByMealType(networkDelegate , typeName, type);

    }

    @Override
    public void getMealDetailsFromNetwork(NetworkDelegateMealDetails networkDelegateMealDetails ,String mealID) {
        ApiClient.getInstance().getMealDetails(networkDelegateMealDetails , mealID);
    }


    public void getCategories(NetworkDelegateSearchInterface network) {
        retrofit.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResultCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                network.onFailureResultCatogeries(t.getMessage());
            }
        });
    }

    public void getAreas(NetworkDelegateSearchInterface network) {
        retrofit.getArea().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResultAreas(response.body().getAreas());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                network.onFailureResultAreas(t.getMessage());

            }
        });
    }


    public void getMealDetails(NetworkDelegateMealDetails network , String mealID) {
        retrofit.getDetailMeal(mealID).enqueue(new Callback<DetailMealResponse>() {
            @Override
            public void onResponse(Call<DetailMealResponse> call, Response<DetailMealResponse> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResult(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<DetailMealResponse> call, Throwable t) {
                network.onFailureResult(t.getMessage());

            }
        });
    }



    public void getIngredients(NetworkDelegateSearchInterface network) {
        retrofit.getIngredient().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful()) {
                    network.onSuccessResultIng(response.body().getIngredients());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                network.onFailureResultIng(t.getMessage());
            }
        });
    }

    public void searchByMealType(NetworkDelegateResultIntefrace networkDelegateResultIntefrace, String typeName,String type) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService =retrofit.create(ApiService.class);


        if(type.equals("area")){
            call=apiService.getMealsByArea(typeName);
        }else if(type.equals("ing")){
            call=apiService.getMealsByIngredient(typeName);
        }else if(type.equals("category")){
            call=apiService.getMealsByCategory(typeName);
        }
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegateResultIntefrace.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegateResultIntefrace.onFailureResult(t.getMessage());
            }
        });


    }
}
