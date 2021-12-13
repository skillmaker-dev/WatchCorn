package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public FavoritesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favorites_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.movieName.setText(movies.get(position).getTitle());

        Glide.with(context).asBitmap().load(movies.get(position).getSmallImageUrl()).error(R.drawable.coming_soon).fallback(R.drawable.coming_soon).into(holder.movieImg);

        holder.movieItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filmPage = new Intent(v.getContext(),MovieDataActivity.class);
                filmPage.putExtra("ImdbId",movies.get(holder.getAdapterPosition()).getImdbID());
                v.getContext().startActivity(filmPage);
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

        private ImageView movieImg;
        private TextView movieName;
        private CardView movieItemParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.imgFavoritesItem);
            movieName = itemView.findViewById(R.id.txtViewMovieName);
            movieItemParent = itemView.findViewById(R.id.favoritesItemParent);


        }
    }
}
