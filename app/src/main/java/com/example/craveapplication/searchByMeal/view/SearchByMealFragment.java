package com.example.craveapplication.searchByMeal.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.craveapplication.R;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.remoteSource.remoteAPI.RemoteSource;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.example.craveapplication.searchByMeal.presenter.SearchByMealPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;


public class SearchByMealFragment extends Fragment implements SearchByMealFragmentInterface {

    private RecyclerView recyclerView;
    private SearchByMealAdapter mealAdapter;
    private SearchByMealPresenter presenter;
    private RemoteSource remoteSource;
    private RepoInterface repo ;
    EditText searchInput;
    TextWatcher textWatcher;
    CalendarView calendarView;
    String formattedDate;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo= Repo.getInstance(    ConcreteLocalSource.getInstance(getContext()) , ApiClient.getInstance(),getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_by_meal, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.search_by_meal_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealAdapter = new SearchByMealAdapter(getContext());
        recyclerView.setAdapter(mealAdapter);
        calendarView = view.findViewById(R.id.calendarView2);
        presenter = new SearchByMealPresenter(this ,repo );
        searchInput = view.findViewById(R.id.searchView);
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list as the user types
                String query = s.toString().trim();
                performSearch(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        };
        searchInput.addTextChangedListener(textWatcher);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                Date date = selectedDate.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                formattedDate = sdf.format(date);
                SharedPreferences sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("message", formattedDate);
                editor.apply();
                System.out.println("Selected Date: " + formattedDate);
            }
        });

    }

    private void performSearch(String query) {
        presenter.getMealsByName(query)
                .flatMapObservable(mealResponse -> Observable.fromIterable(mealResponse.getMeals()))
                .filter(meal -> meal.getStrMeal().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(meals -> {
                    mealAdapter.setMeals(meals);
                }, Throwable::printStackTrace);
    }



}