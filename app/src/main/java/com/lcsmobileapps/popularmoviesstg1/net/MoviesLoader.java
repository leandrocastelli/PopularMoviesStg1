package com.lcsmobileapps.popularmoviesstg1.net;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ProgressBar;


import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.MoviesEntry;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.FavoriteEntry;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.ReviewsEntry;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.TrailersEntry;


import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.FAVORITE;
import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.POPULAR;
import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.TOP_RATED;

/**
 * Created by Leandro on 12/17/2017.
 */

public class MoviesLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    final private IDataReady adapter;
    final private ProgressBar progressBar;
    final private Context context;
    public static final int MOVIES_LOADER_ID = 1;
    public static final String LOAD_CATEGORY = "load_category";
    public MoviesLoader(IDataReady adapter, ProgressBar progressBar) {
        this.adapter = adapter;
        this.progressBar = progressBar;
        this.context = progressBar.getContext();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id != MOVIES_LOADER_ID)
            throw new RuntimeException("Loader Not Implemented: " + id);
        progressBar.setVisibility(View.VISIBLE);
        String category = args.getString(LOAD_CATEGORY);
        new Downloader(context).execute(category);

        switch (category) {
            case  POPULAR: {
                return new CursorLoader(context,
                        MoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MoviesEntry.COLUMN_POPULARITY + " DESC LIMIT 10");
            }
            case TOP_RATED: {
                return new CursorLoader(context,
                        MoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MoviesEntry.COLUMN_VOTE + " DESC LIMIT 10");
            }
            case FAVORITE: {
                String[] projection = { MoviesEntry.TABLE_NAME +"."+MoviesEntry._ID,
                        MoviesEntry.TABLE_NAME +"."+MoviesEntry.COLUMN_POSTER};
                return new CursorLoader(context,
                        FavoriteEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            }

            default:
                throw new RuntimeException("Loader Category Not Implemented: " + category);

        }



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        progressBar.setVisibility(View.INVISIBLE);
        data.setNotificationUri(context.getContentResolver(), MoviesEntry.CONTENT_URI);
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
