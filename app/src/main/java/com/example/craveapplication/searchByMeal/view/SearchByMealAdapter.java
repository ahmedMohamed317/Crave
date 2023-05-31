package com.example.craveapplication.searchByMeal.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.HomeActivity;
import com.example.craveapplication.R;
import com.example.craveapplication.mealDetails.view.MealDetailsActivity;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SearchByMealAdapter extends RecyclerView.Adapter<SearchByMealAdapter.MealViewHolder> {
    private Context context;
    private List<Meal> meals = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mealsCollection = db.collection("meals");
    DocumentReference mealDocument = mealsCollection.document();
    Repo repo = Repo.getInstance( ConcreteLocalSource.getInstance(context) , ApiClient.getInstance(),context);
    public SearchByMealAdapter(Context context) {
        this.context = context;

    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_by_meal_row, parent, false);
        return new MealViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        SharedPreferences sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String message = sharedPrefs.getString("message", "");
        holder.textView.setText(meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (message != null) {
                    meal.setPlan(true);
                    meal.setFav(false);
                    meal.setDate(message);
                    System.out.println(meal.getStrMeal());
                    System.out.println(HomeActivity.userEmail);
                    if (HomeActivity.userEmail == null) {
                        repo.insertMeal(meal);
                    } else {
                        String documentId = HomeActivity.userEmail;
                        DocumentReference mealDocument = mealsCollection.document();
                        meal.setEmail(HomeActivity.userEmail);
                        repo.insertMeal(meal);
                        mealDocument.set(meal)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        System.out.println("Meal is added");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("Meal isn't added due to error on meal doc");
                                    }
                                });


                    }
                    Toast.makeText(context, "Meal is added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Please choose a date", Toast.LENGTH_SHORT).show();

                }
            }});




        holder.constraintLayout.setOnClickListener(view -> {

            Intent intent = new Intent(context, MealDetailsActivity.class);
            intent.putExtra("mealID", meal.getIdMeal());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Button button;
        private ConstraintLayout constraintLayout;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            button = itemView.findViewById(R.id.button);
            constraintLayout = itemView.findViewById((R.id.layout));
        }

        public void bind(Meal meal) {


            button.setOnClickListener(v -> {

            });
        }
    }
}

