package com.natarajan.popularmovies.Controller;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.natarajan.popularmovies.View.Adapter;
import com.natarajan.popularmovies.Model.IntAPIMethods;
import com.natarajan.popularmovies.Model.Movie;
import com.natarajan.popularmovies.Model.MovieDetails;
import com.natarajan.popularmovies.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    List<Movie> movieList;
    String title, overView, releaseDate;
    String voteAvg, posterPath;

    private PopupWindow pw;

    //Adapter and MovieApi
    final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://api.themoviedb.org").build();
    IntAPIMethods movieapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showPopMovie(); // Method called during initial load - Loads popular movies

    }

    //Method that would make a query for fetching the latest movie details by Popularity

    public void showPopMovie() {
        movieapi = restadapter.create(IntAPIMethods.class);
        movieapi.getMovieDetails(new Callback<MovieDetails>() {
            @Override
            public void success(final MovieDetails moviesresponse, Response response) {
                movieList = moviesresponse.getResults();
                Log.i("response", "value is" + moviesresponse.getResults().toString());
                showMovieResults();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Method that would make a query for fetching the latest movie details by Rating

    public void showRateMovie() {
        movieapi = restadapter.create(IntAPIMethods.class);
        movieapi.getMovieHighest(new Callback<MovieDetails>() {
            @Override
            public void success(final MovieDetails moviesresponse, Response response) {
                movieList = moviesresponse.getResults();
                Log.i("response", "value is" + moviesresponse.getResults().toString());
                showMovieResults();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    // Common method used for displaying results. Since the results returned by both SORT by POPULARITY
    // and RATING returns the same format of JSON, we can use this common code.

    public void showMovieResults() {
        GridView gridView = (GridView) findViewById(R.id.gridView);
        Adapter adapt = new Adapter(getApplicationContext(), R.layout.item_file, movieList);
        gridView.setAdapter(adapt);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie movie = movieList.get(position);
                title = movie.getTitle();
                overView = movie.getOverview();
                releaseDate = movie.getRelease_date();
                voteAvg = movie.getVote_average();
                posterPath = movie.getPoster_path();
                Intent intent = new Intent("android.intent.action.SHOWMOVIE");
                intent.putExtra("title", title);
                intent.putExtra("overView", overView);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("voteAvg", voteAvg);
                intent.putExtra("posterPath", posterPath);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "To be Implemented in the future", Toast.LENGTH_SHORT).show();
            return true;

        }

        if (id == R.id.action_sort) {
            try {
                //Instance of the LayoutInflater, use the context of this activity
                LayoutInflater inflater = (LayoutInflater) MainActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflating the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.activity_popup,
                        (ViewGroup) findViewById(R.id.popup_element));
                //popup window
                pw = new PopupWindow(layout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                // display the popup in the center
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

                // get the radioGroup from Popup Window

                RadioGroup radioGroup = (RadioGroup) pw.getContentView().findViewById(R.id.radioGroup);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.sortbypop:
                                showPopMovie();// Call Query for results based on Popularity
                                // Toast.makeText(MainActivity.this, "sort by Pop", Toast.LENGTH_SHORT).show();
                                pw.dismiss();
                                break;
                            case R.id.sortbyrate:
                                showRateMovie(); // Call query for results based on Rating
                                //Toast.makeText(MainActivity.this, "sort by Rate", Toast.LENGTH_SHORT).show();
                                pw.dismiss();
                                break;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


