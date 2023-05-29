package com.example.craveapplication.favourite.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.craveapplication.R;
import com.example.craveapplication.mealDetails.view.MealDetailsActivity;
import com.example.craveapplication.model.Meal;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MealViewHolder> {
    private static final String TAG = "RecycleView";
    private final Context context;
    private List<Meal> meals;
    FavouriteFragmentInterface listener;

    public FavoriteAdapter(Context context, List<Meal> meals , FavouriteFragmentInterface listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_meal_row, parent, false);
        FavoriteAdapter.MealViewHolder viewHolder = new FavoriteAdapter.MealViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.txtTitle.setText(meals.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext())
                .load(meals.get(position).getStrMealThumb())
                .into(holder.imageView);
        holder.unFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteFav(meals.get(position));
                Toast.makeText(context, "Your meal is deleted", Toast.LENGTH_SHORT).show();
            }

        });
        holder.imageView.setOnClickListener(view -> {

            Intent intent =new Intent(context, MealDetailsActivity.class);
            intent.putExtra("mealID",meals.get(position).getIdMeal());
            intent.putExtra("isFav",true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    public class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ConstraintLayout constraintLayout;
        public ImageView imageView;
        public View layout;
        public Button unFav;

        public MealViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            txtTitle = itemView.findViewById(R.id.meal_fav);
            imageView = itemView.findViewById(R.id.thumbnail_fav);
            constraintLayout = itemView.findViewById(R.id.layout_fav);
            unFav=itemView.findViewById(R.id.unfav_button);


        }
    }
}
