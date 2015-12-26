package com.natarajan.popularmovies.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.natarajan.popularmovies.R;
import com.squareup.picasso.Picasso;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Natarajan on 12/23/2015.
 */
public class ShowMovie extends AppCompatActivity {

    String title, overView, releaseDate,imagePoster,voteAvg,releaseYear;
    String imageUrl = "http://image.tmdb.org/t/p/w185";

    @Bind(R.id.origtitle) TextView origTitle_TV;
    @Bind(R.id.overview)  TextView overView_TV;
    @Bind(R.id.voteAverage) TextView voteAverage_TV;
    @Bind(R.id.releaseDate) TextView releaseDate_TV;
    @Bind(R.id.posterimg) ImageView posterImg_View;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        overView = extras.getString("overView");
        overView = overView.length() < 1 ? "No Overview Available":overView; // Found that some movies do not have Overview defined.
        releaseDate = extras.getString("releaseDate");
        releaseYear =  releaseDate.length() > 5? releaseDate.substring(0,4) : releaseYear; // This is done to ensure we do not have String out of Bound exception
        imagePoster = extras.getString("posterPath");
        voteAvg = extras.getString("voteAvg");
        voteAvg = voteAvg + "/10"; // Adding the /10 to vote

        origTitle_TV.setText(title);
        overView_TV.setText(overView);
        voteAverage_TV.setText(voteAvg);
        releaseDate_TV.setText(releaseYear);
        Picasso.with(ShowMovie.this).load(imageUrl+imagePoster).into(posterImg_View);
   }
}