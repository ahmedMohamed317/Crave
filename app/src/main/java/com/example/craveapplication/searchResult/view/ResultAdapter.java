package com.example.craveapplication.searchResult.view;

import android.content.Context;
import android.content.Intent;
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
import com.example.craveapplication.favourite.view.FavouriteFragmentInterface;
import com.example.craveapplication.mealDetails.view.MealDetailsActivity;
import com.example.craveapplication.model.Meal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private Context context;
    private List<Meal> meals;
    String type ;
    private SearchResultInterface listener ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mealsCollection = db.collection("meals");
    DocumentReference mealDocument = mealsCollection.document();



    public ResultAdapter(Context context, List<Meal> meals , SearchResultInterface listener , String type  ) {
        this.context = context;
        this.meals= meals;
        this.type = type ;
        this.listener=listener;

    }

    public Context getContext() {
        return context;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.result_row,parent,false);
        ResultViewHolder viewHolder=new ResultViewHolder(view);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        Meal meal = meals.get(position);
        holder.txtTitle.setText(meals.get(position).getStrMeal());
        holder.type.setText(type);
        Glide.with(holder.itemView.getContext())
                .load(meals.get(position).getStrMealThumb())
                .into(holder.imageView);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(HomeActivity.userEmail);
                if ( HomeActivity.userEmail==null)
                    {listener.addFav(meal);}
                else
                {
                    String documentId = HomeActivity.userEmail ;
                    DocumentReference mealDocument = mealsCollection.document();
                    meal.setEmail(HomeActivity.userEmail);
                    listener.addFav(meal);
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
        });
        holder.constraintLayout.setOnClickListener(view -> {

            Intent intent =new Intent(context, MealDetailsActivity.class);
            intent.putExtra("mealID",meals.get(position).getIdMeal());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });




        };



    @Override
    public int getItemCount() {
         return meals.size();
    }


    public class ResultViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView type;
        public ConstraintLayout constraintLayout;
        public View layout;
        public ImageView imageView;
        public Button favorite;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            txtTitle=itemView.findViewById( R.id.meal_result);
            type=itemView.findViewById( R.id.type_result);
            imageView=itemView.findViewById(R.id.thumbnail_result);
            constraintLayout=itemView.findViewById(R.id.layout_result);
            favorite=itemView.findViewById(R.id.fav_button);
        }
    }
    public void setResult(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();

    }
}
