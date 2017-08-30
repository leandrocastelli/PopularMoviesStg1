package com.lcsmobileapps.popularmoviesstg1.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by leandro.silverio on 24/08/2017.
 */
@Parcel
public class Movie {
    String original_title;
    String poster_path;
    String overview;
    String vote_average;
    String release_date;

    @ParcelConstructor
    public Movie(String original_title, String poster_path, String overview, String vote_average, String release_date) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String getOriginal_title() {

        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



}
