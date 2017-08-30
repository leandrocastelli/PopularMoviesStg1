package com.lcsmobileapps.popularmoviesstg1.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import static com.lcsmobileapps.popularmoviesstg1.BuildConfig.API_KEY;

/**
 * Created by leandro.silverio on 29/08/2017.
 */

public class Utils {
    public static URL buildURL (String queryParameter) {

        Uri uri = Uri.parse(Constants.URL_MOVIE_BASE + queryParameter).buildUpon()
                .appendQueryParameter(Constants.URL_API, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
