package com.example.flixster_part_2.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster_part_2.DetailActivity;
import com.example.flixster_part_2.R;
import com.example.flixster_part_2.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    private static final String TAG = "MovieAdapter";


    public MovieAdapter(Context context, List<Movie> movies) {
        Log.i(TAG, "Creating Adapter");
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating View holder");
        View MovieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(MovieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.i(TAG, "Binding to view holder");
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    // Custom View holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declare class values
        RelativeLayout rlContainer;
        TextView title;
        TextView overview;
        ImageView poster;

        // init variables to their corresponding view
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            overview = itemView.findViewById(R.id.tvOverview);
            poster = itemView.findViewById(R.id.ivPoster);
            rlContainer = itemView.findViewById(R.id.rlContainer);
        }

        // bind data to views
        public void bind(Movie movie) {
            Log.i(TAG, "binding " + movie.getTitle());
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());

            // set up the image
            Log.d(TAG, movie.getPosterPath());
            String imageUrl;
            /*
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            else{
                imageUrl = movie.getBackdropPath();

            }*/
            Glide.with(context)
                    .load(movie.getPosterPath())
                    .into(poster);

            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }
}
