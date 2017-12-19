package com.lcsmobileapps.popularmoviesstg1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcsmobileapps.popularmoviesstg1.model.Review;

import java.util.List;

/**
 * Created by Leandro on 12/18/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {
    Context context;
    List<Review> dataSet;

    public ReviewsAdapter(Context context, List<Review> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        Review review = dataSet.get(position);
        holder.oontentReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        TextView oontentReview;

        public ReviewHolder(View itemView) {
            super(itemView);
            oontentReview = (TextView)itemView.findViewById(android.R.id.text1);

        }
    }
}
