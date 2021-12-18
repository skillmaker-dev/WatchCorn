package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapterForRecyclerView extends RecyclerView.Adapter<MoviesAdapterForRecyclerView.ViewHolder> {

    private ArrayList<Movie> resMovie = new ArrayList<>();
    private Context context;
    private DB db;

    public MoviesAdapterForRecyclerView(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_for_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        db = new DB(context);

        Picasso.get().load(resMovie.get(position).getSmallImageUrl()).placeholder(R.drawable.loading).into(holder.coverMovie);

        holder.titleMovie.setText(resMovie.get(position).getTitle());
        holder.yearMovie.setText((resMovie.get(position).getReleaseYear() != null && !resMovie.get(position).getReleaseYear().isEmpty()  )? resMovie.get(position).getReleaseYear().substring(0,4) : " " );
        holder.filmRating.setText(resMovie.get(position).getRating());

        if (db.checkMovieInFavorites(resMovie.get(holder.getAdapterPosition()).getImdbID())) {
            holder.favButton.setBackgroundResource(R.drawable.favorite_checked);
        } else {
            holder.favButton.setBackgroundResource(R.drawable.favorite_unchecked);
        }
        holder.favButton.setVisibility(View.VISIBLE);

        if (db.checkMovieInWatchList(resMovie.get(holder.getAdapterPosition()).getImdbID())) {
            holder.watchButton.setBackgroundResource(R.drawable.watchlist_checked);
        } else {
            holder.watchButton.setBackgroundResource(R.drawable.bookmark_unchecked);
        }
        holder.watchButton.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filmPage = new Intent(v.getContext(),MovieDataActivity.class);
                filmPage.putExtra("ImdbId",resMovie.get(holder.getAdapterPosition()).getImdbID());
                v.getContext().startActivity(filmPage);

                //Toast.makeText(context, movies.get(holder.getAdapterPosition()).getTitle() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInFavorites(resMovie.get(holder.getAdapterPosition()).getImdbID())) {
                    holder.favButton.setBackgroundResource(R.drawable.favorite_unchecked);
                    db.removeFromFavorites(resMovie.get(holder.getAdapterPosition()).getImdbID());
                    //db.decrementGenreTotalSelected(resMovie.get(holder.getAdapterPosition()).getGenres().get(0).toLowerCase());
                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    holder.favButton.setBackgroundResource(R.drawable.favorite_checked);
                    db.insertIntoFavorites(resMovie.get(holder.getAdapterPosition()).getImdbID());
                    //db.incrementGenreTotalSelected(resMovie.get(holder.getAdapterPosition()).getGenres().get(0).toLowerCase());
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInWatchList(resMovie.get(holder.getAdapterPosition()).getImdbID())) {
                    holder.watchButton.setBackgroundResource(R.drawable.bookmark_unchecked);
                    db.removeFromWatchList(resMovie.get(holder.getAdapterPosition()).getImdbID());
                    Toast.makeText(context, "Removed from watchList", Toast.LENGTH_SHORT).show();
                } else {
                    holder.watchButton.setBackgroundResource(R.drawable.watchlist_checked);
                    db.insertIntoWatchList(resMovie.get(holder.getAdapterPosition()).getImdbID());
                    Toast.makeText(context, "Added to watchList", Toast.LENGTH_SHORT).show();
                }
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return resMovie.size();
    }

    public void setResMovie(ArrayList<Movie> resMovie) {
        this.resMovie = resMovie;
        notifyItemInserted(resMovie.size()-1);
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
