package com.example.craveapplication.mealDetails.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.craveapplication.HomeActivity;
import com.example.craveapplication.R;
import com.example.craveapplication.favourite.presenter.FavouritePresenter;
import com.example.craveapplication.mealDetails.presenter.MealsDetailsPresenter;
import com.example.craveapplication.model.DetailMeal;
import com.example.craveapplication.model.Meal;
import com.example.craveapplication.model.MealIngredients;
import com.example.craveapplication.remoteSource.remoteAPI.ApiClient;
import com.example.craveapplication.remoteSource.remoteAPI.RemoteSource;
import com.example.craveapplication.roomDatabase.ConcreteLocalSource;
import com.example.craveapplication.roomDatabase.Repo;
import com.example.craveapplication.roomDatabase.RepoInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsActivity extends AppCompatActivity implements MealDetailsActivityInterface {
    RecyclerView rvIngredients;
    MealsDetailsPresenter mealsDetailsPresenter;
    MealIngeridentsAdapter adapter;
    DetailMeal meal = new DetailMeal();
    RepoInterface repo ;
    List<MealIngredients> ingredientsList;
    RemoteSource client;
    ImageView imageView;
    TextView strArea;
    TextView procedure;
    TextView allProcedure;
    TextView category;
    TextView mealName;
    YouTubePlayerView youTubePlayerView;
    ImageView favImg;
    boolean isFav = false ;
    FavouritePresenter favouritePresenter ;
    Meal mealFav;
    CollectionReference mealsCollection ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        repo = Repo.getInstance( ConcreteLocalSource.getInstance(getApplicationContext()) , ApiClient.getInstance(),getApplicationContext());
        favouritePresenter = new FavouritePresenter(repo);
        mealsDetailsPresenter = new MealsDetailsPresenter(this , repo) ;
        String mealID=getIntent().getStringExtra("mealID");
        boolean isFavFromFavouritePage = getIntent().getBooleanExtra("isFav",false);
        mealsDetailsPresenter.getMealDetails(mealID);
        imageView = findViewById(R.id.mealImagedetails);
        category = findViewById(R.id.textViewMealCateItemDetails);
        strArea = findViewById(R.id.textViewMealCountryItemDetails);
        procedure = findViewById(R.id.procedurre);
        mealName = findViewById(R.id.txtViewMealNameItemDetails);
        favImg = findViewById(R.id.imageViewAddToFavITemDetails);
        if (isFavFromFavouritePage){favImg.setImageResource(R.drawable.ic_baseline_favorite_24);}
        allProcedure = findViewById(R.id.allprocedures);
        youTubePlayerView=findViewById(R.id.ytPlayer);
        getLifecycle().addObserver(youTubePlayerView);
        rvIngredients = findViewById(R.id.recyclerViewIngredientsItemDetails);
        ingredientsList = new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvIngredients.setLayoutManager(layoutManager);
        adapter = new MealIngeridentsAdapter(getApplicationContext(),ingredientsList);
        rvIngredients.setAdapter(adapter);
        mealFav = new Meal();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mealsCollection = db.collection("meals");

        favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( HomeActivity.userEmail==null){
                    if(isFavFromFavouritePage){
                        isFav=true;

                    }
                    if(isFav){
                        favImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        isFav=false;
                        favouritePresenter.deleteFav(mealFav);
                        Toast.makeText(getApplicationContext(), "The meal is deleted", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        favImg.setImageResource(R.drawable.ic_baseline_favorite_24);
                        isFav=true;
                        favouritePresenter.addFav(mealFav);
                        //Toast.makeText(getApplicationContext(), "The meal is added to favorite list", Toast.LENGTH_SHORT).show();


                    }
                }
                else
                {
                    if(isFavFromFavouritePage){
                        isFav=true;

                    }
                    if(isFav){
                        favImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        isFav=false;
                        favouritePresenter.deleteFav(mealFav);
                        Toast.makeText(getApplicationContext(), "Your meal is deleted", Toast.LENGTH_SHORT).show();
                        deleteFromFirebase("idMeal",mealFav.getIdMeal());

                    }
                    else {
                        favImg.setImageResource(R.drawable.ic_baseline_favorite_24);
                        isFav=true;
                        favouritePresenter.addFav(mealFav);
                        DocumentReference mealDocument = mealsCollection.document();
                        mealFav.setEmail(HomeActivity.userEmail);
                        favouritePresenter.addFav(mealFav);
                        mealDocument.set(mealFav)
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
            }
            }
        });







    }

    @Override
    public void showData(DetailMeal detailMeal) {
        meal = detailMeal;
        mealFav.setIdMeal(meal.getIdMeal());
        mealFav.setStrMeal(meal.getStrMeal());
        mealFav.setStrMealThumb(meal.getStrMealThumb());
        mealName.setText(meal.getStrMeal());
        strArea.setText(meal.getStrArea());
        category.setText(meal.getStrCategory());
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .centerCrop()
                .into(imageView);
        allProcedure.setText(meal.getStrInstructions());
        fillIngredientList(meal);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = parseYoutubeURL(meal.getStrYoutube());
                youTubePlayer.cueVideo(videoId, 0);
                youTubePlayer.pause();
            }
        });

    }
    public void fillIngredientList(DetailMeal meal){

        if(meal.getIng1().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng1()+ "-Small.png",meal.getMeas1(),meal.getIng1()));
        }
        if(meal.getIng2().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng2()+ "-Small.png",meal.getMeas2(),meal.getIng2()));
        }
        if(meal.getIng3().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng3()+ "-Small.png",meal.getMeas3(),meal.getIng3()));
        }
        if(meal.getIng4().isEmpty()==false){

            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng4()+ "-Small.png",meal.getMeas4(),meal.getIng4()));
        }
        if(meal.getIng5().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng5()+ "-Small.png",meal.getMeas5(),meal.getIng5()));
        }
        if(meal.getIng6().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng6()+ "-Small.png",meal.getMeas6(),meal.getIng6()));
        }
        if(meal.getIng7().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng7()+ "-Small.png",meal.getMeas7(),meal.getIng7()));
        }
        if(meal.getIng8().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng8()+ "-Small.png",meal.getMeas8(),meal.getIng8()));
        }
        if(meal.getIng9().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng9()+ "-Small.png",meal.getMeas9(),meal.getIng9()));
        }
        if(meal.getIng10().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng10()+ "-Small.png",meal.getMeas10(),meal.getIng10()));
        }
        if(meal.getIng11().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng11()+ "-Small.png",meal.getMeas11(),meal.getIng11()));
        }
        if(meal.getIng12().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng12()+ "-Small.png",meal.getMeas12(),meal.getIng12()));
        }
        if(meal.getIng13().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng13()+ "-Small.png",meal.getMeas13(),meal.getIng13()));
        }
        if(meal.getIng14().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng14()+ "-Small.png",meal.getMeas14(),meal.getIng14()));
        }

        if(meal.getIng15().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng15()+ "-Small.png",meal.getMeas15(),meal.getIng15()));
        }

        if(meal.getIng16().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng16()+ "-Small.png",meal.getMeas16(),meal.getIng16()));
        }
        if(meal.getIng17().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng17()+ "-Small.png",meal.getMeas17(),meal.getIng17()));
        }
        if(meal.getIng18().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng18()+ "-Small.png",meal.getMeas18(),meal.getIng18()));
        }
        if(meal.getIng19().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng19()+ "-Small.png",meal.getMeas19(),meal.getIng19()));
        }
        if(meal.getIng20().isEmpty()==false){
            ingredientsList.add(new MealIngredients("https://www.themealdb.com/images/ingredients/" + meal.getIng20()+ "-Small.png",meal.getMeas20(),meal.getIng20()));
        }

        adapter.setList(ingredientsList);
        adapter.notifyDataSetChanged();

    }
    public String parseYoutubeURL(String youtubeID) {
        String youtubeUrl = youtubeID;
        String videoId = null;

        Uri uri = Uri.parse(youtubeUrl);
        if (uri.getQueryParameter("v") != null) {
            videoId = uri.getQueryParameter("v");
        } else {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() >= 2) {
                videoId = pathSegments.get(1);
            }
        }
        return videoId;
    }

    public void deleteFromFirebase(String fieldName ,String fieldValue )

    {
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
                            Toast.makeText(getApplicationContext(), "Your meal isn't deleted on firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });







    }

}