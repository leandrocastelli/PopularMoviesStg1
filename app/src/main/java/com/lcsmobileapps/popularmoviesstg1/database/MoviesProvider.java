package com.lcsmobileapps.popularmoviesstg1.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.lcsmobileapps.popularmoviesstg1.database.MoviesDbHelper.DATABASE_NAME;

/**
 * Created by Leandro on 12/16/2017.
 */

public class MoviesProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static final int REVIEWS = 200;
    public static final int REVIEW_WITH_ID = 201;
    public static final int TRAILERS = 300;
    public static final int TRAILER_WITH_ID = 301;
    public static final int FAVORITES = 400;
    public static final int FAVORITE_WITH_ID = 401;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MoviesDbHelper moviesDbHelper;
    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_REVIEWS, REVIEWS);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_REVIEWS + "/#", REVIEW_WITH_ID);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_TRAILERS, TRAILERS);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_TRAILERS + "/#", TRAILER_WITH_ID);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAVORITE, FAVORITES);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAVORITE + "/#", FAVORITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = moviesDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        String table = null;
        switch (match){
            case FAVORITES: {
                SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
                sqLiteQueryBuilder.setTables(MoviesContract.MoviesEntry.TABLE_NAME + " INNER JOIN " +
                        MoviesContract.FavoriteEntry.TABLE_NAME + " ON " +
                        MoviesContract.MoviesEntry.TABLE_NAME+"."+ MoviesContract.MoviesEntry._ID +
                        " = " + MoviesContract.FavoriteEntry.TABLE_NAME+"."+ MoviesContract.FavoriteEntry._ID);

                cursor = sqLiteQueryBuilder.query(sqLiteDatabase,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            }
            case MOVIES: {
                table = MoviesContract.MoviesEntry.TABLE_NAME;
            }break;
            case REVIEWS: {
                table = MoviesContract.ReviewsEntry.TABLE_NAME;
            }break;
            case TRAILERS: {
                table = MoviesContract.TrailersEntry.TABLE_NAME;
            }break;
            case FAVORITE_WITH_ID: {
                table = MoviesContract.FavoriteEntry.TABLE_NAME;
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor = sqLiteDatabase.query(table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        long id = -1;
        switch (match){
            case MOVIES: {
                id = sqLiteDatabase.insertWithOnConflict(MoviesContract.MoviesEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.MoviesEntry.CONTENT_URI, id);
                }
            }break;
            case REVIEWS: {
                id = sqLiteDatabase.insertWithOnConflict(MoviesContract.ReviewsEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.ReviewsEntry.CONTENT_URI, id);
                }
            }break;
            case TRAILERS: {
                id = sqLiteDatabase.insertWithOnConflict(MoviesContract.TrailersEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.TrailersEntry.CONTENT_URI, id);
                }

            }break;
            case FAVORITE_WITH_ID: {
                id = sqLiteDatabase.insertWithOnConflict(MoviesContract.FavoriteEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI, id);
                }

            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int rows = -1;
        switch (match){
            case MOVIES: {
                rows = sqLiteDatabase.updateWithOnConflict(MoviesContract.MoviesEntry.TABLE_NAME, values,
                        selection,
                        selectionArgs,
                        SQLiteDatabase.CONFLICT_REPLACE);

            }break;
            case REVIEWS: {
                rows = sqLiteDatabase.updateWithOnConflict(MoviesContract.ReviewsEntry.TABLE_NAME,values,
                        selection,
                        selectionArgs,
                        SQLiteDatabase.CONFLICT_REPLACE);
            }break;
            case TRAILERS: {
                rows = sqLiteDatabase.updateWithOnConflict(MoviesContract.TrailersEntry.TABLE_NAME, values,
                        selection,
                        selectionArgs,
                        SQLiteDatabase.CONFLICT_REPLACE);
            }break;
            case FAVORITE_WITH_ID: {
                rows = sqLiteDatabase.updateWithOnConflict(MoviesContract.FavoriteEntry.TABLE_NAME, values,
                        selection,
                        selectionArgs,
                        SQLiteDatabase.CONFLICT_REPLACE);
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }
}
