package com.natarajan.popularmovies.Model;

import com.natarajan.popularmovies.Model.MovieDetails;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
/**
 * Created by Natarajan on 12/23/2015.
 */
public interface IntAPIMethods {

    //Retrofit turns the query as Java API Interface
    // Method for getting Movie details by Popularity
    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=YOUR_API_KEY")
    public  void  getMovieDetails(Callback<MovieDetails> response);

    // Method for getting Movie details by Rating
    @GET("/3/discover/movie?sort_by=vote_average.desc&api_key=YOUR_API_KEY")
    public  void  getMovieHighest(Callback<MovieDetails> response);

}
