package com.example.craveapplication.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.R;
import com.example.craveapplication.home.view.RandomMealAdapter;
import com.example.craveapplication.model.Area;
import com.example.craveapplication.model.CategoryPojo;
import com.example.craveapplication.model.Ingredient;
import com.example.craveapplication.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder>{
    private Context context;
    private List<Area> areaList;
    private SearchFragmentInterface listener ;


    public AreaAdapter(Context context, List<Area> areas , SearchFragmentInterface listener ) {
        this.context = context;
        this.areaList= areas;
        this.listener=listener;
    }
    public Context getContext() {
        return context;
    }

    public List<Area> getAreas() {
        return areaList;
    }

    public void setAreas(List<Area> areas) {
        this.areaList = areas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AreaAdapter.AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.country_row, parent, false);
        return new AreaAdapter.AreaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.AreaViewHolder holder, int position) {
        Area area = areaList.get(position);
        holder.txtTitle.setText(areaList.get(position).getStrArea());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOnArea(getContext() ,area.getStrArea(),"area");

            }
        });

    }





    public class AreaViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ConstraintLayout constraintLayout;
        public View layout;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            txtTitle = itemView.findViewById(R.id.countryStr);
            constraintLayout = itemView.findViewById(R.id.countryParent);
        }
    }
    @Override
    public int getItemCount() {
        return areaList.size();
    }
}
