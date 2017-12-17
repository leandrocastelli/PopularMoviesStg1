package com.lcsmobileapps.popularmoviesstg1;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

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
        getContentResolver().update(MoviesContract.MoviesEntry.CONTENT_URI, movie.toContentValues(),
                MoviesContract.MoviesEntry._ID + " = " + movie.getId(), null);
    }
}
