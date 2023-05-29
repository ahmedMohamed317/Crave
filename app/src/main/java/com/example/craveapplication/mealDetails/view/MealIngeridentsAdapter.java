package com.example.craveapplication.mealDetails.view;

import android.content.Context;
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
import com.example.craveapplication.home.view.RandomMealAdapter;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.model.MealIngredients;

import java.util.List;

public class MealIngeridentsAdapter extends RecyclerView.Adapter<MealIngeridentsAdapter.ViewHolder> {


    private Context context;
    private List<MealIngredients> ingredients;

    public MealIngeridentsAdapter(Context context, List<MealIngredients> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.ingredients_item_meal_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MealIngredients ing = ingredients.get(position);
        holder.nameTextView1.setText(ing.getIngredientName());
        holder.measure.setText(ing.getMeasure());
        Glide.with(context)
                .load(ing.getIngredientThumb())
                .placeholder(R.drawable.placeholder)
                .into(holder.ingImageView);

    }



    @Override
    public int getItemCount() {
        return (ingredients.size());
    }

    public void setList(List<MealIngredients> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingImageView;
        TextView nameTextView1;
        TextView measure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingImageView = itemView.findViewById(R.id.imageViewIngredientImageItem_mealDetails);
            nameTextView1 = itemView.findViewById(R.id.textViewIngredientNameItem_mealDetails);
            measure = itemView.findViewById(R.id.textViewIngredientMeasureItem);


        }
    }
}






