package com.lcsmobileapps.popularmoviesstg1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.MoviesEntry;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.ReviewsEntry;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.TrailersEntry;

/**
 * Created by Leandro on 12/16/2017.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies.db";

    private static final int VERSION = 2;
    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_TITLE                + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_POPULARITY               + " REAL, " +
                MoviesEntry.COLUMN_POSTER                + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW                + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VOTE                + " REAL , " +
                MoviesEntry.COLUMN_RELEASE                + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_FAVORITE                + " INTEGER); ";

        final String CREATE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS " + ReviewsEntry.TABLE_NAME + " (" +
                ReviewsEntry._ID                + " TEXT PRIMARY KEY, " +
                ReviewsEntry.COLUMN_AUTHOR                + " TEXT NOT NULL, " +
                ReviewsEntry.COLUMN_CONTENT                + " TEXT NOT NULL, " +
                ReviewsEntry.COLUMN_MOVIE_ID                + " INTEGER); ";

        final String CREATE_TRAILERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TrailersEntry.TABLE_NAME + " (" +
                TrailersEntry._ID                + " TEXT PRIMARY KEY, " +
                TrailersEntry.COLUMN_KEY                + " TEXT NOT NULL, " +
                TrailersEntry.COLUMN_NAME                + " TEXT NOT NULL, " +
                TrailersEntry.COLUMN_MOVIE_ID                + " INTEGER); ";
        db.execSQL(CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
        db.execSQL(CREATE_TRAILERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailersEntry.TABLE_NAME);
        onCreate(db);
    }
}
