package com.lcsmobileapps.popularmoviesstg1.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.MoviesEntry;

/**
 * Created by leandro.silverio on 24/08/2017.
 */
@Parcel
public class Movie {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String original_title;

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    String popularity;
    String poster_path;
    String overview;
    String vote_average;
    String release_date;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    int favorite;

    @ParcelConstructor
    public Movie(String id, String original_title,String popularity, String poster_path, String overview, String vote_average, String release_date, int favorite) {
        this.id = id;
        this.original_title = original_title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.favorite = favorite;
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

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesEntry._ID, id);
        contentValues.put(MoviesEntry.COLUMN_TITLE, original_title);
        contentValues.put(MoviesEntry.COLUMN_POSTER, poster_path);
        contentValues.put(MoviesEntry.COLUMN_POPULARITY, popularity);
        contentValues.put(MoviesEntry.COLUMN_OVERVIEW, overview);
        contentValues.put(MoviesEntry.COLUMN_VOTE, vote_average);
        contentValues.put(MoviesEntry.COLUMN_RELEASE, release_date);
        contentValues.put(MoviesEntry.COLUMN_FAVORITE, favorite);
        return contentValues;
    }

    public Movie(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MoviesEntry._ID);
        int titleIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_TITLE);
        int posterIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_POSTER);
        int popularityIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_POPULARITY);
        int overviewIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_OVERVIEW);
        int voteIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_VOTE);
        int releaseIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_RELEASE);
        int favoriteIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_FAVORITE);

        this.id = String.valueOf(cursor.getInt(idIndex));
        this.original_title = cursor.getString(titleIndex);
        this.poster_path = cursor.getString(posterIndex);
        this.popularity = String.valueOf(cursor.getFloat(popularityIndex));
        this.overview = cursor.getString(overviewIndex);
        this.vote_average = String.valueOf(cursor.getFloat(voteIndex));
        this.release_date = cursor.getString(releaseIndex);
        this.favorite = cursor.getInt(favoriteIndex);
    }

}
