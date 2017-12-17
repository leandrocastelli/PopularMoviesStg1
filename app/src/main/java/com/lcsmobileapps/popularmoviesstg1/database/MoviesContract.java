package com.lcsmobileapps.popularmoviesstg1.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Leandro on 12/16/2017.
 */

public class MoviesContract {
    public static final String AUTHORITY = "com.lcsmobileapps.popularmoviesstg1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_TRAILERS = "trailers";
    public static final String PATH_REVIEWS = "reviews";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES)
                .build();
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE = "movie_title";
        public static final String COLUMN_POPULARITY = "movie_popularity";
        public static final String COLUMN_POSTER = "movie_poster";
        public static final String COLUMN_OVERVIEW = "movie_overview";
        public static final String COLUMN_VOTE = "movie_vote";
        public static final String COLUMN_RELEASE = "movie_release";
        public static final String COLUMN_FAVORITE = "movie_favorite";
    }

    public static final class ReviewsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS)
                .build();
        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_AUTHOR = "review_author";
        public static final String COLUMN_CONTENT = "review_content";
        public static final String COLUMN_MOVIE_ID = "review_movie_id";

    }

    public static final class TrailersEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS)
                .build();
        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_KEY = "trailers_key";
        public static final String COLUMN_NAME = "trailers_name";
        public static final String COLUMN_MOVIE_ID = "review_movie_id";
    }
}
