package com.example.craveapplication.home.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.R;
import com.example.craveapplication.mealDetails.view.MealDetailsActivity;
import com.example.craveapplication.model.Meal;
import com.google.android.material.imageview.ShapeableImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
public class SuggestedMealAdapter extends RecyclerView.Adapter<SuggestedMealAdapter.ViewHolder> {
    private Context context;
    private List<Meal> suggestedMeals;

    public SuggestedMealAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.suggestedMeals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meals_suggested_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index1 = position * 2;
        int index2 = index1 + 1;

        if (index1 < suggestedMeals.size()) {
            Meal meal1 = suggestedMeals.get(index1);
            holder.titleMealTextView1.setText(meal1.getStrMeal());
            Glide.with(context)
                    .load(meal1.getStrMealThumb())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.mealImageView1);
            holder.cardView1.setOnClickListener(view -> {

                Intent intent =new Intent(context, MealDetailsActivity.class);
                intent.putExtra("mealID",meal1.getIdMeal());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            });

        } else {
            holder.cardView1.setVisibility(View.INVISIBLE);
        }

       if (index2 < suggestedMeals.size()) {
            Meal meal2 = suggestedMeals.get(index2);
            holder.titleMealTextView2.setText(meal2.getStrMeal());
            Glide.with(context)
                    .load(meal2.getStrMealThumb())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.mealImageView2);
            holder.cardView2.setVisibility(View.VISIBLE);
           holder.cardView2.setOnClickListener(view -> {

               Intent intent =new Intent(context, MealDetailsActivity.class);
               intent.putExtra("mealID",meal2.getIdMeal());
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);

           });

       } else {
            holder.cardView2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        return (int) Math.ceil(suggestedMeals.size() / 2.0);
    }

    public void setList(List<Meal> meals) {
        this.suggestedMeals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView1;
        CardView cardView2;
        ImageView mealImageView1;
        ImageView mealImageView2;
        TextView titleMealTextView1;
        TextView titleMealTextView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView1 = itemView.findViewById(R.id.cardViewWeekPlanRow1);
            cardView2 = itemView.findViewById(R.id.cardViewWeekPlanRow2);
            mealImageView1 = itemView.findViewById(R.id.meal_imageView);
            mealImageView2 = itemView.findViewById(R.id.meal_imageView2);
            titleMealTextView1 = itemView.findViewById(R.id.titleMeal_textView);
            titleMealTextView2 = itemView.findViewById(R.id.titleMeal_textView2);
        }
    }
}


