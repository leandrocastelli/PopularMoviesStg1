package com.lcsmobileapps.popularmoviesstg1.model;

import android.content.ContentValues;


import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;

import org.parceler.Parcel;

/**
 * Created by Leandro on 12/16/2017.
 */
public class Trailer {

    String key;
    String name;
    String id;



    public Trailer(String id, String key, String title) {
        this.id = id;
        this.key = key;
        this.name = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContentValues toContentValues(String movieID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.TrailersEntry._ID, id);
        contentValues.put(MoviesContract.TrailersEntry.COLUMN_KEY, key);
        contentValues.put(MoviesContract.TrailersEntry.COLUMN_NAME, name);
        contentValues.put(MoviesContract.TrailersEntry.COLUMN_MOVIE_ID, movieID);
        return contentValues;

    }

}
