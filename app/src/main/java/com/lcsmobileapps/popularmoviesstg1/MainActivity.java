package com.lcsmobileapps.popularmoviesstg1;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import com.lcsmobileapps.popularmoviesstg1.adapter.MoviesAdapter;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.net.Downloader;
import com.lcsmobileapps.popularmoviesstg1.net.IDataReady;
import com.lcsmobileapps.popularmoviesstg1.net.MoviesLoader;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.lcsmobileapps.popularmoviesstg1.utils.MoviesPreference;

import org.parceler.Parcels;

import java.util.List;

import static com.lcsmobileapps.popularmoviesstg1.net.MoviesLoader.LOAD_CATEGORY;
import static com.lcsmobileapps.popularmoviesstg1.net.MoviesLoader.MOVIES_LOADER_ID;
import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.EXTRA_POSITION;
import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.EXTRA_STATE;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProgressBar progressBar;
    GridLayoutManager.SavedState mSavedState;
    private int last_position = -1;

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

        MoviesAdapter adapter = new MoviesAdapter();
        rv.setAdapter(adapter);
        updateAdapter();
        rv.setLayoutManager(gridLayoutManager);
        if (savedInstanceState != null ){
            mSavedState = savedInstanceState.getParcelable(EXTRA_STATE);
            last_position = savedInstanceState.getInt(EXTRA_POSITION);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (last_position == -1)
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rv.getLayoutManager().onRestoreInstanceState(mSavedState);
                rv.getLayoutManager().scrollToPosition(last_position);
                rv.scrollToPosition(last_position);
            }
        }, 200);
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
                int currentChecked = 0;

                switch ( preference) {
                    case Constants.TOP_RATED: {
                        currentChecked = 0;
                    }break;
                    case Constants.POPULAR: {
                        currentChecked = 1;
                    }break;
                    case Constants.FAVORITE: {
                        currentChecked = 2;
                    }break;
                }

                final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.settings)
                        .setSingleChoiceItems(new CharSequence[]{getString(R.string.top_rated) , getString(R.string.popular),
                                getString(R.string.favorite)},
                                currentChecked, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                MoviesPreference.setMoviePreference(MainActivity.this, Constants.TOP_RATED);
                                break;
                            case 1:
                                MoviesPreference.setMoviePreference(MainActivity.this, Constants.POPULAR);
                                break;
                            case 2:
                                MoviesPreference.setMoviePreference(MainActivity.this, Constants.FAVORITE);
                                break;
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
        super.onSaveInstanceState(outState);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) rv.getLayoutManager();

        outState.putParcelable(EXTRA_STATE, gridLayoutManager.onSaveInstanceState());
        outState.putInt(EXTRA_POSITION, gridLayoutManager.findFirstVisibleItemPosition());

    }

    private void updateAdapter() {
        String preference = MoviesPreference.getMoviePreference(this);
        Bundle bundle = new Bundle();
        bundle.putString(LOAD_CATEGORY, preference);
        Loader<Cursor> loader = getSupportLoaderManager().getLoader(MoviesLoader.MOVIES_LOADER_ID);
        if (loader == null)
            getSupportLoaderManager().initLoader(MoviesLoader.MOVIES_LOADER_ID, bundle, new MoviesLoader((IDataReady)rv.getAdapter(), progressBar));
        else
            getSupportLoaderManager().restartLoader(MoviesLoader.MOVIES_LOADER_ID, bundle, new MoviesLoader((IDataReady)rv.getAdapter(), progressBar));
    }

}
