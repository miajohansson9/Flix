package com.example.flix;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flix.models.Config;
import com.example.flix.models.Movie;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {
    RatingBar rbVoteAverage;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        String overview = getIntent().getStringExtra("movieOverview");
        String title = getIntent().getStringExtra("movieTitle");
        String moviePosterURL = getIntent().getStringExtra("moviePosterURL");
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        float voteAverage = (float) getIntent().getDoubleExtra("movieRating", 0.0);

        TextView titleTextview = findViewById(R.id.movieTitle);
        TextView overviewTextview = findViewById(R.id.description);
        ImageView posterView = findViewById(R.id.moviePoster);
        int placeholderId = R.drawable.flicks_backdrop_placeholder;

        Glide.with(this)
                .load(moviePosterURL)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(placeholderId)
                .into(posterView);

        titleTextview.setText(title);
        overviewTextview.setText(overview);
        rbVoteAverage.setRating(voteAverage = voteAverage > 0.0 ? voteAverage / 2.0f : voteAverage);
    }
}
