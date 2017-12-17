package com.lcsmobileapps.popularmoviesstg1.net;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.model.Review;
import com.lcsmobileapps.popularmoviesstg1.model.Trailer;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.lcsmobileapps.popularmoviesstg1.utils.Utils;

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

/**
 * Created by leandro.silverio on 24/08/2017.
 */

public class Downloader extends AsyncTask<String, Void, Void>{

    final private Context context;
    public Downloader(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params[0] == FAVORITE)
            return null;
        String json = getJson(Utils.buildURL(params[0]));
        if (json == null) {
            return null;
        }
        JSONArray results;
        try {
            JSONObject jsonObject = new JSONObject(json);
            results = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            //wrong json format.
            return null;
        }
        Gson gson = new GsonBuilder().create();
        Type movieType = new TypeToken<ArrayList<Movie>>(){}.getType();
        List<Movie> movieList = gson.fromJson(String.valueOf(results), movieType);
        for (Movie movie : movieList) {
            context.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI,
                    movie.toContentValues());

            json = getJson(Utils.buildURL(movie.getId() + "/" + Constants.REVIEWS));
            if (json != null) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    results = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    //wrong json format.
                    return null;
                }
                Type reviewsType = new TypeToken<ArrayList<Review>>(){}.getType();
                List<Review> reviews = gson.fromJson(String.valueOf(results), reviewsType);
                for (Review review : reviews) {
                    context.getContentResolver().insert(MoviesContract.ReviewsEntry.CONTENT_URI,
                            review.toContentValues(movie.getId()));
                }
            }

            json = getJson(Utils.buildURL(movie.getId() + "/" + Constants.VIDEOS));
            if (json != null) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    results = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    //wrong json format.
                    return null;
                }
                Type trailersType = new TypeToken<ArrayList<Trailer>>(){}.getType();
                List<Trailer> trailers = gson.fromJson(String.valueOf(results), trailersType);
                for (Trailer trailer : trailers) {
                    context.getContentResolver().insert(MoviesContract.TrailersEntry.CONTENT_URI,
                            trailer.toContentValues(movie.getId()));
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressBar.setVisibility(View.VISIBLE);
    }


    private String getJson(URL url){



        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
}
