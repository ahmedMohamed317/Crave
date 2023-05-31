package com.example.craveapplication.favourite.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.craveapplication.HomeActivity;
import com.example.craveapplication.R;
import com.example.craveapplication.favourite.presenter.FavouritePresenter;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.searchResult.view.ResultAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavouriteFragmentInterface {

    Repo repo;
    FavouritePresenter presenter;
    FavoriteAdapter favMealAdapter;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    FavouriteFragmentInterface fav ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mealsCollection = db.collection("meals");
    DocumentReference mealDocument = mealsCollection.document();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getContext()) , ApiClient.getInstance(),getContext());
        //loadUserMeals(HomeActivity.userEmail);
        favMealAdapter=new FavoriteAdapter(getContext(),new ArrayList<>() , this);
        presenter = new FavouritePresenter( repo);
        fav = this;
        db = FirebaseFirestore.getInstance();
        mealsCollection = db.collection("meals");
        mealDocument = mealsCollection.document();
        loadUserMeals(HomeActivity.userEmail);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.fav_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        favMealAdapter=new FavoriteAdapter(getContext(), new ArrayList<>(),this);
        recyclerView.setAdapter(favMealAdapter);


        repo.getCachedMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favMealAdapter.setMeals(meals);
                favMealAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void addFav(Meal meal) {
        presenter.addFav(meal);

    }

    public void deleteFav(Meal meal) {
        presenter.deleteFav(meal);

    }

    public LiveData<List<Meal>> getFav() {
         return presenter.getFav();

    }
    public void loadUserMeals(String userId) {
        repo.getUserMeals(userId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Meal> meals = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Meal meal = document.toObject(Meal.class);
                        meals.add(meal);
                        System.out.println(meal.getStrMeal());
                        addFav(meal);
                    }
                    System.out.println("succeded at the fav fragment");


                } else {
                        System.out.println("Failed at the fav fragment");
                }
            }
        });
    }



}