package com.lcsmobileapps.popularmoviesstg1.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcsmobileapps.popularmoviesstg1.DetailActivity;
import com.lcsmobileapps.popularmoviesstg1.R;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.model.Movie;
import com.lcsmobileapps.popularmoviesstg1.net.IDataReady;
import com.lcsmobileapps.popularmoviesstg1.utils.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;


/**
 * Created by leandro.silverio on 24/08/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> implements IDataReady {

    private Cursor mCursor;
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(R.layout.item_list, parent, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        int posterIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER);
        int idIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry._ID);

        mCursor.moveToPosition(position);

        String posterPath = mCursor.getString(posterIndex);
        final int id = mCursor.getInt(idIndex);

        Picasso.with(holder.posterImg.getContext()).load(Constants.URL_IMAGE_BASE + posterPath)
                .into(holder.posterImg);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(Constants.EXTRA_MOVIE, id);
                v.getContext().startActivity(intent);
                mCursor.close();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }


    class MovieHolder extends RecyclerView.ViewHolder {

        ImageView posterImg;
        CardView cardView;
        public MovieHolder(View itemView) {
            super(itemView);
            posterImg = (ImageView)itemView.findViewById(R.id.iv_poster_list);
            cardView = (CardView)itemView.findViewById(R.id.cv_poster_holder);

        }
    }
    @Override
    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        // After the new Cursor is set, call notifyDataSetChanged
        notifyDataSetChanged();
    }
}
