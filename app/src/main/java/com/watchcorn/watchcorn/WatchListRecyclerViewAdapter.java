package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WatchListRecyclerViewAdapter extends RecyclerView.Adapter<WatchListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;
    private DB db;

    public WatchListRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_for_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        db = new DB(context);

        holder.titleMovie.setText(movies.get(position).getTitle());
        holder.yearMovie.setText((movies.get(position).getReleaseYear() != null && !movies.get(position).getReleaseYear().isEmpty()  )? movies.get(position).getReleaseYear().substring(0,4) : " " );
        holder.filmRating.setText(movies.get(position).getRating());

        Glide.with(context).asBitmap().load(movies.get(position).getSmallImageUrl()).error(R.drawable.coming_soon).fallback(R.drawable.coming_soon).into(holder.coverMovie);

        if (db.checkMovieInWatchList(movies.get(holder.getAdapterPosition()).getImdbID())) {
            holder.watchButton.setBackgroundResource(R.drawable.watchlist_checked);
        } else {
            holder.watchButton.setBackgroundResource(R.drawable.bookmark_unchecked);
        }
        holder.watchButton.setVisibility(View.VISIBLE);

        holder.favButton.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filmPage = new Intent(v.getContext(),MovieDataActivity.class);
                filmPage.putExtra("ImdbId",movies.get(holder.getAdapterPosition()).getImdbID());
                v.getContext().startActivity(filmPage);
            }
        });

        holder.watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInWatchList(movies.get(holder.getAdapterPosition()).getImdbID())) {
                    holder.watchButton.setBackgroundResource(R.drawable.bookmark_unchecked);
                    db.removeFromWatchList(movies.get(holder.getAdapterPosition()).getImdbID());
                    Toast.makeText(context, "Removed from watchList", Toast.LENGTH_SHORT).show();
                } else {
                    holder.watchButton.setBackgroundResource(R.drawable.watchlist_checked);
                    db.insertIntoWatchList(movies.get(holder.getAdapterPosition()).getImdbID());
                    Toast.makeText(context, "Added to watchList", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleMovie,yearMovie,filmRating;
        private ImageView coverMovie;
        private CardView myLayout;
        private Button favButton, watchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            filmRating = itemView.findViewById(R.id.txtViewMovieRate);
            titleMovie = itemView.findViewById(R.id.txtViewMovieName);
            yearMovie = itemView.findViewById(R.id.txtViewMovieYear);
            coverMovie = itemView.findViewById(R.id.imgMovieItem);
            myLayout = itemView.findViewById(R.id.movietItemParent);
            favButton = itemView.findViewById(R.id.favBtn);
            watchButton = itemView.findViewById(R.id.watchBtn);

        }
    }
}
