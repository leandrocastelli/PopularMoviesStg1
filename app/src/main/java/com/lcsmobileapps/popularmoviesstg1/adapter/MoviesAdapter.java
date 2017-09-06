package com.lcsmobileapps.popularmoviesstg1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcsmobileapps.popularmoviesstg1.DetailActivity;
import com.lcsmobileapps.popularmoviesstg1.R;
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

    public void setMoviesData(List<Movie> moviesData) {
        this.moviesData = moviesData;
        notifyDataSetChanged();
    }
    public List<Movie> getMoviesData() {
        return moviesData;
    }

    private List<Movie> moviesData;
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(R.layout.item_list, parent, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        final Movie movie = moviesData.get(position);
        Picasso.with(holder.posterImg.getContext()).load(Constants.URL_IMAGE_BASE + movie.getPoster_path())
                .into(holder.posterImg);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(Constants.EXTRA_MOVIE, Parcels.wrap(movie));
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (moviesData != null) {
            return moviesData.size();
        }
        return 0;
    }

    @Override
    public void onDataReady(List<Movie> moviesData) {
        this.moviesData = moviesData;
        notifyDataSetChanged();
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
}
