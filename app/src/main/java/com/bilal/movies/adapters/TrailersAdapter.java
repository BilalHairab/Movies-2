package com.bilal.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilal.movies.R;
import com.bilal.movies.models.Trailer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bilal on 05/03/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {
    private Context context;
    private List<Trailer> trailers = new ArrayList<>();

    public TrailersAdapter(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    private OnTrailerClickListener onMovieClickListener = new OnTrailerClickListener() {
        @Override
        public void onTrailerClick(int position) {
            Intent toTrailer = new Intent(Intent.ACTION_VIEW);
            toTrailer.setData(Uri.parse(trailers.get(position).getYouTubeKey()));
            context.startActivity(toTrailer);
        }
    };

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_trailer, parent, false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        String placeHolder = context.getString(R.string.trailer) + " " + (position + 1);
        holder.trailerText.setText(placeHolder);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_trailer)
        TextView trailerText;

        TrailerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onMovieClickListener.onTrailerClick(position);
        }
    }

    public interface OnTrailerClickListener {
        void onTrailerClick(int position);
    }
}
