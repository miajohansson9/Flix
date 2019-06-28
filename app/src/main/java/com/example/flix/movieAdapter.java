package com.example.flix;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flix.models.Config;
import com.example.flix.models.Movie;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder> {
    ArrayList<Movie> movies;
    Config config;
    Context context;

    public movieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        String imageURL;
        if (isPortrait) {
            imageURL = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
            viewHolder.tvPosterImage.setOnClickListener(view -> seeMovieDetails(i));
        } else {
            imageURL = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
            viewHolder.tvBackdropImage.setOnClickListener(view -> seeMovieDetails(i));
        }

        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? viewHolder.tvPosterImage : viewHolder.tvBackdropImage;

        Glide.with(context)
                .load(imageURL)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(placeholderId)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tvPosterImage;
        ImageView tvBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPosterImage = (ImageView) itemView.findViewById(R.id.tvPosterImage);
            tvBackdropImage = (ImageView) itemView.findViewById(R.id.tvBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    private void seeMovieDetails(int position) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("movieOverview", movies.get(position).getOverview());
        intent.putExtra("movieTitle", movies.get(position).getTitle());
        intent.putExtra("movieRating", movies.get(position).getVoteAverage());
        intent.putExtra("moviePosterURL", config.getImageUrl(config.getBackdropSize(), movies.get(position).getBackdropPath()));
        context.startActivity(intent);
    }
}
