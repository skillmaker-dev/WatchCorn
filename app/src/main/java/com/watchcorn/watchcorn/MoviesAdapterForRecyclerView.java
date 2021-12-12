package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private ArrayList<Movie> resMovie1;
    private Context context;

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
        Picasso.get().load(resMovie.get(position).getSmallImageUrl()).placeholder(R.drawable.loading).into(holder.coverMovie);
        holder.titleMovie.setText(resMovie.get(position).getTitle());
        holder.yearMovie.setText((resMovie.get(position).getReleaseYear() != null && !resMovie.get(position).getReleaseYear().isEmpty()  )? resMovie.get(position).getReleaseYear().substring(0,4) : " " );
        holder.filmRating.setText(resMovie.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filmPage = new Intent(v.getContext(),MovieDataActivity.class);
                filmPage.putExtra("ImdbId",resMovie.get(holder.getAdapterPosition()).getImdbID());
                v.getContext().startActivity(filmPage);

                //Toast.makeText(context, movies.get(holder.getAdapterPosition()).getTitle() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
        //Glide.with(context).asBitmap().load(resMovie.get(position).getSmallImageUrl()).into(holder.coverMovie);

        /*
            add the glide dependencies to be able to load images from the internet :
                implementation 'com.github.bumptech.glide:glide:4.12.0'
                annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
        */

        /*
            add the permission into the androidManifest.xml file :
                <uses-permission android:name="android.permission.Internet"/>
        */

        /*
            add the following lines of code :
                Glide.with(context).asBitmap().load(artists.get(position).getImgUrl()).into(holder.artistImg);
        */
    }

    @Override
    public int getItemCount() {
        return resMovie.size();
    }

    public void setResMovie(ArrayList<Movie> resMovie) {
        this.resMovie = resMovie;
        //notifyDataSetChanged();
        notifyItemInserted(resMovie.size()-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private ImageView artistImg;
        private TextView titleMovie,yearMovie,filmRating;
        private ImageView coverMovie;
        private RelativeLayout myLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filmRating = itemView.findViewById(R.id.film_rating);
            titleMovie = itemView.findViewById(R.id.text);
            yearMovie = itemView.findViewById(R.id.text2);
            coverMovie = itemView.findViewById(R.id.haha);
            myLayout = itemView.findViewById(R.id.parent);


        }
    }
}
