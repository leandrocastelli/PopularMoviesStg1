package com.lcsmobileapps.popularmoviesstg1;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.lcsmobileapps.popularmoviesstg1.adapter.MoviesAdapter;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.net.Downloader;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.lcsmobileapps.popularmoviesstg1.utils.MoviesPreference;

import org.parceler.Parcels;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView)findViewById(R.id.rv_movies_list);
        progressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        int columnsNumber;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsNumber = 3;
        } else {
            columnsNumber = 2;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsNumber);

        rv.setLayoutManager(gridLayoutManager);
        MoviesAdapter adapter = new MoviesAdapter();
        rv.setAdapter(adapter);
        if (savedInstanceState != null) {
            List<Movie> tempList = Parcels.unwrap(savedInstanceState.getParcelable(Constants.KEY_DATASET));
            adapter.setMoviesData(tempList);
        } else {
            updateAdapter();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:

                String preference = MoviesPreference.getMoviePreference(this);
                int currentChecked = Constants.TOP_RATED.equals(preference)?0:1;

                final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.settings)
                        .setSingleChoiceItems(new CharSequence[]{getString(R.string.top_rated) , getString(R.string.popular)},
                                currentChecked, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                MoviesPreference.setMoviePreference(MainActivity.this, Constants.TOP_RATED);
                            } else {
                                MoviesPreference.setMoviePreference(MainActivity.this, Constants.POPULAR);
                            }
                            updateAdapter();

                        dialog.dismiss();
                    }
                }) .create();
                dialog.show();
                return true;
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        MoviesAdapter adapter = (MoviesAdapter)rv.getAdapter();
        List<Movie> movieArrayList = adapter.getMoviesData();
        outState.putParcelable(Constants.KEY_DATASET, Parcels.wrap(movieArrayList));
        super.onSaveInstanceState(outState);

    }

    private void updateAdapter() {
        String preference = MoviesPreference.getMoviePreference(this);
        new Downloader((MoviesAdapter) rv.getAdapter(), progressBar).execute(preference);
    }

}
