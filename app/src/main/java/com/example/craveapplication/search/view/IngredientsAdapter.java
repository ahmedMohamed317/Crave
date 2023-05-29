package com.example.craveapplication.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.R;
import com.example.craveapplication.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private final Context context;
    private List<Ingredient> ingredientsList;
    private SearchFragmentInterface listener ;

    public Context getContext() {
        return context;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;

    }

    public void setIngredientsList(List<Ingredient> ingredientItems) {
        this.ingredientsList = ingredientItems;
        notifyDataSetChanged();

    }

    public IngredientsAdapter(Context context, List<Ingredient> ingredientsList,SearchFragmentInterface listener) {
        this.context = context;
        this.ingredientsList = ingredientsList;
        this.listener=listener;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.ingredient_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredientsList.get(position);
        String url = "https://www.themealdb.com/images/ingredients/" + ingredientsList.get(position).getStrIngredient() + "-Small.png";
        holder.txtTitle.setText(ingredientsList.get(position).getStrIngredient());
        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOnIngredients(getContext() ,ingredient.getStrIngredient(),"ing");

            }
        });

    }




    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle;
        public ConstraintLayout constraintLayout;
        public ImageView imageView;
        public View layout;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            txtTitle = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.thumbnail);
            constraintLayout = itemView.findViewById(R.id.ingParent);
        }
    }
    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

}

