package com.example.flix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private Double voteAverage;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() { return backdropPath; }

    public Double getVoteAverage() { return voteAverage; }
}
