package com.example.craveapplication.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.craveapplication.R;
import com.example.craveapplication.model.CategoryPojo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private List<CategoryPojo> categoryItems;
    private SearchFragmentInterface listener ;

    public Context getContext() {
        return context;
    }

    public List<CategoryPojo> getCategoryItems() {
        return categoryItems;

    }

    public void setCategoryItems(List<CategoryPojo> categoryItems) {
        this.categoryItems = categoryItems;
        notifyDataSetChanged();

    }

    private static final String TAG="RecycleView";
    public CategoryAdapter(Context context, List<CategoryPojo> categoryItems , SearchFragmentInterface listener) {
        this.context = context;
        this.categoryItems = categoryItems;
        this.listener = listener;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.category_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryPojo category = categoryItems.get(position);
        holder.txtTitle.setText(categoryItems.get(position).getStrCategory());
        Glide.with(holder.itemView.getContext())
                .load(categoryItems.get(position).getStrCategoryThumb())
                .into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOnCategory(getContext() ,category.getStrCategory(),"category");

            }
        });

    }




    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle;

        public ConstraintLayout constraintLayout;
        public View layout;
        public CircleImageView imageView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            txtTitle=itemView.findViewById( R.id.strCategory);
            imageView=itemView.findViewById(R.id.circleImageView);
            constraintLayout=itemView.findViewById(R.id.pParent);
        }
    }
    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

}

