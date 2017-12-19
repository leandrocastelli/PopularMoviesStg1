package com.lcsmobileapps.popularmoviesstg1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcsmobileapps.popularmoviesstg1.R;
import com.lcsmobileapps.popularmoviesstg1.model.Trailer;

import java.util.List;

import static com.lcsmobileapps.popularmoviesstg1.utils.Constants.URL_YOUTUBE;

/**
 * Created by Leandro on 12/18/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder>{

    Context context;
    List<Trailer> dataSet;

    public TrailersAdapter(Context context, List<Trailer> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }
    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(R.layout.trailer_item_list, parent, false);

        return new TrailerHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        final Trailer trailer = dataSet.get(position);

        holder.trailerTitle.setText(trailer.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_YOUTUBE + trailer.getKey()));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        TextView trailerTitle;
        CardView cardView;
        public TrailerHolder(View itemView) {
            super(itemView);
            trailerTitle = (TextView)itemView.findViewById(R.id.tv_trailer_text);
            cardView = (CardView)itemView.findViewById(R.id.cv_trailer_holder);

        }
    }
}
