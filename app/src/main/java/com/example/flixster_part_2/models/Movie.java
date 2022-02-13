package com.example.flixster_part_2.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    int movieId;
    String title;
    String overview;
    String backdropPath;
    String posterPath;
    String releaseDate;
    double rating;
    Boolean adultMovie;
    private static final String TAG = "Movie";
    private static final String posterPathStatic = "https://image.tmdb.org/t/p/w342%s";


    public Movie() {}


    public Movie(JSONObject jsonObject) throws JSONException {
        setTitle(jsonObject.getString("title"));
        setOverview(jsonObject.getString("overview"));
        setPosterPath(jsonObject.getString("poster_path"));
        setRating(jsonObject.getDouble("vote_average"));
        setMovieId(jsonObject.getInt("id"));
        //backdropPath =jsonObject.getString("backdrop_path");
        //adultMovie = jsonObject.getBoolean("adult");
        //releaseDate = jsonObject.getString("release_date");
    }



    static public List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> ReturnMovies = new ArrayList<>();


        for (int i = 0; i < jsonArray.length(); i++) {
            ReturnMovies.add(new Movie(jsonArray.getJSONObject(i)));
            Log.d(TAG, String.format(posterPathStatic, ReturnMovies.get(i).posterPath));
        }

        return ReturnMovies;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public Boolean getAdultrating(){
        return adultMovie;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return String.format(posterPathStatic, posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}

