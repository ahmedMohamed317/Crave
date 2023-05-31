package com.example.craveapplication.searchByMeal.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.R;
import com.example.craveapplication.mealDetails.view.MealDetailsActivity;
import com.example.craveapplication.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchByMealAdapter extends RecyclerView.Adapter<SearchByMealAdapter.MealViewHolder> {
    private Context context;
    private List<Meal> meals = new ArrayList<>();
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
        holder.textView.setText(meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
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

