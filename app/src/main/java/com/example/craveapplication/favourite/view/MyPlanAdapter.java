package com.example.craveapplication.favourite.view;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyPlanAdapter extends RecyclerView.Adapter<MyPlanAdapter.MealViewHolder>{

    private static final String TAG = "RecycleView";
    private final Context context;
    private List<Meal> meals;
    FavouriteFragmentInterface listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mealsCollection = db.collection("meals");
    TextView dateInfo;
    SharedPreferences sharedPrefs ;


    public MyPlanAdapter(Context context, List<Meal> meals, FavouriteFragmentInterface listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        List<Meal> favoriteMeals = new ArrayList<>();

        for (Meal meal : meals) {
            if (meal.getPlan()) {
                favoriteMeals.add(meal);
            }
        }

        this.meals = favoriteMeals;
    }


    @NonNull
    @Override
    public MyPlanAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.myplan_meal_row, parent, false);
        MyPlanAdapter.MealViewHolder viewHolder = new MyPlanAdapter.MealViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String message = sharedPrefs.getString("message", "");
            holder.txtTitle.setText(meals.get(position).getStrMeal());
            holder.dateInfo.setText(meals.get(position).getDate());
            Glide.with(holder.itemView.getContext())
                    .load(meals.get(position).getStrMealThumb())
                    .into(holder.imageView);
            holder.unFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HomeActivity.userEmail == null) {
                        listener.deleteFav(meals.get(position));
                        Toast.makeText(context, "Your meal is deleted", Toast.LENGTH_SHORT).show();
                    } else {

                        listener.deleteFav(meals.get(position));
                        Toast.makeText(context, "Your meal is deleted", Toast.LENGTH_SHORT).show();
                        deleteFromFirebase("idMeal", meals.get(position).getIdMeal());


                    }


                }

            });
            holder.imageView.setOnClickListener(view -> {

                Intent intent = new Intent(context, MealDetailsActivity.class);
                intent.putExtra("mealID", meals.get(position).getIdMeal());
                intent.putExtra("isFav", true);
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
        public TextView dateInfo;



        public MealViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            txtTitle = itemView.findViewById(R.id.meal_fav);
            imageView = itemView.findViewById(R.id.thumbnail_fav);
            constraintLayout = itemView.findViewById(R.id.layout_fav);
            unFav = itemView.findViewById(R.id.unfav_button);
            dateInfo=itemView.findViewById(R.id.dateinfoo);


        }
    }

    public void deleteFromFirebase(String fieldName, String fieldValue) {
        mealsCollection.whereEqualTo(fieldName, fieldValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Iterate through the matching documents and delete them
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            // Handle success
                        } else {
                            // Handle error
                            Toast.makeText(context.getApplicationContext(), "Your meal isn't deleted on firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}


