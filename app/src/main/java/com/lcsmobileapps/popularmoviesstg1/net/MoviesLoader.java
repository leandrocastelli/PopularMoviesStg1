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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MoviesContract.MoviesEntry.COLUMN_POPULARITY + " DESC");
            }
            case TOP_RATED: {
                return new CursorLoader(context,
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MoviesContract.MoviesEntry.COLUMN_VOTE + " DESC");
            }
            case FAVORITE: {
                return new CursorLoader(context,
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        MoviesContract.MoviesEntry.COLUMN_FAVORITE + " = 1",
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
        data.setNotificationUri(context.getContentResolver(), MoviesContract.MoviesEntry.CONTENT_URI);
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
