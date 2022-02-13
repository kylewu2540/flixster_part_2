package com.example.flixster_part_2;


import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_part_2.R;
import com.example.flixster_part_2.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyCHlf4FwYBTEonF0lcQ559Hj3gtBdLVTTQ";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "DetailActivity";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    TextView tvReleasedate;
    TextView tvAdult;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvOverview = findViewById(R.id.tvDetailOverview);
        tvReleasedate = findViewById(R.id.tvReleasedate);
        ratingBar = findViewById(R.id.tvDetailRatingBar);
        youTubePlayerView = findViewById(R.id.ytPlayerView);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        /*
        tvReleasedate.setText("Release Date: "+movie.getReleaseDate());
        if (movie.getAdultrating() == true){
            tvAdult.setText("Adult Movie: Yes");
        }else{
            tvAdult.setText("Adult Movie: No");
        }*/

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d(TAG, youtubeKey);
                    Log.d("DetailActivity", String.valueOf(movie.getMovieId()));
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, response);
            }
        });
    }

    private void initializeYoutube(String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializeSuccess");
                //Log.d(TAG, youtubeKey);
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onFailureSuccess");
            }
        });
    }
}