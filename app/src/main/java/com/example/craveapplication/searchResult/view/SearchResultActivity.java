package com.example.craveapplication.searchResult.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.craveapplication.R;
import com.example.craveapplication.favourite.view.FavouriteFragmentInterface;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.searchResult.presenter.SearchResultPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements SearchResultInterface  {
    List<Meal> meals = new ArrayList<>();
    RepoInterface repo ;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    SearchResultPresenter resultPresenter;
    String data;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        data = getIntent().getStringExtra("myData");
        type=getIntent().getStringExtra("type");
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getApplicationContext()) , ApiClient.getInstance(),getApplicationContext());
        recyclerView = findViewById(R.id.result_recycle_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext().getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        resultAdapter = new ResultAdapter(getApplicationContext(),  meals ,this ,data);
        recyclerView.setAdapter(resultAdapter);
        resultPresenter = new SearchResultPresenter( this , repo , data , type);
        resultPresenter.getResults();
    }


    @Override
    public void showResult(List<Meal> meals) {

        this.meals=meals;
        resultAdapter.setResult(meals);

    }
    public void addFav(Meal meal) {

        resultPresenter.addFav(meal);
    }




}