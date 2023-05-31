package com.example.craveapplication.favourite.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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

public class MyPlanActivity extends AppCompatActivity implements FavouriteFragmentInterface {

    Repo repo;
    FavouritePresenter presenter;
    MyPlanAdapter planMealAdapter;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    FavouriteFragmentInterface fav ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mealsCollection = db.collection("meals");
    DocumentReference mealDocument = mealsCollection.document();
    TextView dateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan_activityy);
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getApplicationContext()) , ApiClient.getInstance(),getApplicationContext());
        planMealAdapter=new MyPlanAdapter(getApplicationContext(),new ArrayList<>() , this);
        presenter = new FavouritePresenter( repo);
        fav = this;
        db = FirebaseFirestore.getInstance();
        mealsCollection = db.collection("meals");
        mealDocument = mealsCollection.document();
        loadUserMeals(HomeActivity.userEmail);

        recyclerView=findViewById(R.id.plan_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        planMealAdapter=new MyPlanAdapter(getApplicationContext(), new ArrayList<>(),this);
        recyclerView.setAdapter(planMealAdapter);


        repo.getCachedMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                planMealAdapter.setMeals(meals);
                planMealAdapter.notifyDataSetChanged();

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
                    System.out.println("succeded at the plan ");


                } else {
                    System.out.println("Failed at the plan ");
                }
            }
        });
    }

}