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

import java.util.List;

public class RandomMealAdapter extends RecyclerView.Adapter<RandomMealAdapter.ViewHolder> {
    private Context context;
    private List<Meal> randomMeal;

    public RandomMealAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.randomMeal = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.preview_random_meal_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Meal meal1 = randomMeal.get(position);
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


    }

    @Override
    public int getItemCount() {
        return (randomMeal.size());
    }

    public void setList(List<Meal> meals) {
        this.randomMeal = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView1;
        ImageView mealImageView1;
        TextView titleMealTextView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView1 = itemView.findViewById(R.id.previewMeal_cardView);
            mealImageView1 = itemView.findViewById(R.id.previewMeal_imageView);
            titleMealTextView1 = itemView.findViewById(R.id.titlePreviewMeal_textView);
        }
    }
}


