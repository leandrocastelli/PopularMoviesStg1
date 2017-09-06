package com.lcsmobileapps.popularmoviesstg1.net;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
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

/**
 * Created by leandro.silverio on 24/08/2017.
 */

public class Downloader extends AsyncTask<String, Void, List<Movie>>{

    final private IDataReady adapter;
    final private ProgressBar progressBar;
    public Downloader(IDataReady adapter, ProgressBar progressBar) {
        this.adapter = adapter;
        this.progressBar = progressBar;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

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
        return  gson.fromJson(String.valueOf(results), movieType);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (adapter != null) {
            //Update List
            adapter.onDataReady(movies);
        }

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
