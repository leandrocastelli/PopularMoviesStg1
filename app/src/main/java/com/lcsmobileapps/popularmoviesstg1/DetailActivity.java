package com.lcsmobileapps.popularmoviesstg1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcsmobileapps.popularmoviesstg1.adapter.MoviesAdapter;
import com.lcsmobileapps.popularmoviesstg1.adapter.ReviewsAdapter;
import com.lcsmobileapps.popularmoviesstg1.adapter.TrailersAdapter;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.model.Review;
import com.lcsmobileapps.popularmoviesstg1.model.Trailer;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    private Movie movie;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_detail_date) TextView tvYear;
    @BindView(R.id.tv_rate) TextView tvVote;
    @BindView(R.id.tv_detail_description) TextView tvDescription;
    @BindView(R.id.backdrop) ImageView ivPoster;
    @BindView(R.id.iv_favorite) ImageView ivFavorite;
    @BindView(R.id.rv_trailers_list) RecyclerView rvTrailers;
    @BindView(R.id.rv_review_list) RecyclerView rvReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customized);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null) {
            movie = Parcels.unwrap(savedInstanceState.getParcelable(Constants.KEY_DATASET));
        } else {
            if (getIntent().hasExtra(Constants.EXTRA_MOVIE)) {
                int id = getIntent().getIntExtra(Constants.EXTRA_MOVIE, 0);
                Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        MoviesContract.MoviesEntry._ID + " = " + id,
                        null,
                        null);
                if (cursor.moveToFirst()) {
                    movie = new Movie(cursor);
                    cursor.close();
                }
                cursor = getContentResolver().query(MoviesContract.FavoriteEntry.CONTENT_URI.buildUpon()
                        .appendPath(movie.getId()).build(),
                        null,
                        MoviesContract.FavoriteEntry._ID + " = " + id,
                        null,
                        null);
                if (cursor.moveToFirst()) {
                     int index = cursor.getColumnIndex(MoviesContract.FavoriteEntry.COLUMN_FAVORITE);
                    movie.setFavorite(cursor.getInt(index));
                    cursor.close();
                }
            }

        }

        getSupportActionBar().setTitle(movie.getOriginal_title());

        tvYear.setText(movie.getRelease_date());
        String average = movie.getVote_average() + getString(R.string.slash_10);
        tvVote.setText(average);

        tvDescription.setText(movie.getOverview());
        Picasso.with(this).load(Constants.URL_IMAGE_BASE + movie.getPoster_path())
                .into(ivPoster);
        if (movie.getFavorite() == 0) {
            ivFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        } else {
            ivFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }

        Cursor cursor = getContentResolver().query(MoviesContract.TrailersEntry.CONTENT_URI,
                null,
                MoviesContract.TrailersEntry.COLUMN_MOVIE_ID + " = " + movie.getId(),
                null,
                null);
        if (cursor != null) {

            List<Trailer> trailers = new ArrayList<Trailer>();
            while (cursor.moveToNext()) {
                trailers.add(new Trailer(cursor));
            }
            cursor.close();

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            rvTrailers.setLayoutManager(layoutManager);
            rvTrailers.setAdapter(new TrailersAdapter(this, trailers));

        }

        cursor = getContentResolver().query(MoviesContract.ReviewsEntry.CONTENT_URI,
                null,
                MoviesContract.ReviewsEntry.COLUMN_MOVIE_ID + " = " + movie.getId(),
                null,
                null);
        if (cursor != null) {

            List<Review> reviews = new ArrayList<Review>();
            while (cursor.moveToNext()) {
                reviews.add(new Review(cursor));
            }
            cursor.close();

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            rvReviews.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                    layoutManager.getOrientation());
            rvReviews.setAdapter(new ReviewsAdapter(this, reviews));
            rvReviews.addItemDecoration(dividerItemDecoration);

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.KEY_DATASET, Parcels.wrap(movie));
        super.onSaveInstanceState(outState);
    }
    @OnClick (R.id.iv_favorite)
    public void starClick(View view) {
        if (movie.getFavorite() == 0) {
            movie.setFavorite(1);
            ivFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            movie.setFavorite(0);
            ivFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.FavoriteEntry._ID, movie.getId());
        contentValues.put(MoviesContract.FavoriteEntry.COLUMN_FAVORITE, movie.getFavorite());
        getContentResolver().insert(MoviesContract.FavoriteEntry.CONTENT_URI.buildUpon()
                .appendPath(movie.getId()).build(), contentValues);
    }
}
