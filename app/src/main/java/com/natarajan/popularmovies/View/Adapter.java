package com.natarajan.popularmovies.View;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.natarajan.popularmovies.Model.Movie;
import com.natarajan.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by Natarajan on 12/23/2015.
 */


public class Adapter extends ArrayAdapter<Movie> {
    //Image is fetched using this url string
    // Gives the complete path as we append this with poster_path
    String url = "http://image.tmdb.org/t/p/w185";
    private Context context;
    private List<Movie> movieList;
    public Adapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movieList = objects;
    }



    // Method that gets called implicitly when Adapter instance is used
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // The item_file contains Image View for the Grid to be populated
        View view = inflater.inflate(R.layout.item_file, parent, false);
        Movie movie = movieList.get(position);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        img.setAdjustViewBounds(true); // ensures images occupy the grid properly
        Picasso.with(getContext()).load(url+movie.getPoster_path()).into(img);// Picasso loads the image from source URL
        return view;
    }
}

