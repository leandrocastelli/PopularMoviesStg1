package com.lcsmobileapps.popularmoviesstg1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by leandro.silverio on 28/08/2017.
 */

public class MoviesPreference {

    public static String getMoviePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.MOVIE_PREFERENCE, Constants.TOP_RATED);
    }

    public static void setMoviePreference(Context context, String value) {
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        sharedPreferences.putString(Constants.MOVIE_PREFERENCE, value);
        sharedPreferences.apply();

    }
}
