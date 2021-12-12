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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewUpcomingMoviesAdapter extends RecyclerView.Adapter<RecyclerViewUpcomingMoviesAdapter.ViewHolder> {

    private ArrayList<Movie> upcomingMovies = new ArrayList<>();
    private Context context;

    public RecyclerViewUpcomingMoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_page_upcoming_movies_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtViewUpcomingMovieName.setText(upcomingMovies.get(position).getTitle());
        Glide.with(context).asBitmap().load(upcomingMovies.get(position).getSmallImageUrl()).into(holder.imgUpcomingMovieItem);

        holder.upcomingMoviesItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filmPage = new Intent(v.getContext(),MovieDataActivity.class);
                Log.d("imdb",upcomingMovies.get(holder.getAdapterPosition()).getImdbID() +"test");
                filmPage.putExtra("ImdbId",upcomingMovies.get(holder.getAdapterPosition()).getImdbID());
                v.getContext().startActivity(filmPage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return upcomingMovies.size();
    }

    public void setUpcomingMovies(ArrayList<Movie> upcomingMovies) {
        this.upcomingMovies = upcomingMovies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUpcomingMovieItem;
        private TextView txtViewUpcomingMovieName;
        private CardView upcomingMoviesItemParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUpcomingMovieItem = itemView.findViewById(R.id.imgUpcomingMovieItem);
            txtViewUpcomingMovieName = itemView.findViewById(R.id.txtViewUpcomingMovieName);
            upcomingMoviesItemParent = itemView.findViewById(R.id.upcomingMoviesItemParent);


        }
    }
}
