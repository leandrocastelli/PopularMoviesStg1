package com.lcsmobileapps.popularmoviesstg1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private Movie movie;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_detail_date) TextView tvYear;
    @BindView(R.id.tv_rate) TextView tvVote;
    @BindView(R.id.tv_detail_description) TextView tvDescription;
    @BindView(R.id.backdrop) ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customized);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra(Constants.EXTRA_MOVIE)) {
            movie = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MOVIE));
        } else {
            if (savedInstanceState != null) {
                movie = Parcels.unwrap(savedInstanceState.getParcelable(Constants.KEY_DATASET));
            }
        }

        getSupportActionBar().setTitle(movie.getOriginal_title());

        tvYear.setText(movie.getRelease_date());
        String average = movie.getVote_average() + getString(R.string.slash_10);
        tvVote.setText(average);

        tvDescription.setText(movie.getOverview());
        Picasso.with(this).load(Constants.URL_IMAGE_BASE + movie.getPoster_path())
                .into(ivPoster);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.KEY_DATASET, Parcels.wrap(movie));
        super.onSaveInstanceState(outState);
    }
}
