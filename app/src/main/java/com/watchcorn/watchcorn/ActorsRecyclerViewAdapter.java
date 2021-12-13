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

public class ActorsRecyclerViewAdapter extends RecyclerView.Adapter<ActorsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Actor> actors = new ArrayList<>();
    private Context context;

    public ActorsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_page_movie_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.actorName.setText(actors.get(position).getName());
        //Picasso.get().load(movies.get(position).getSmallImageUrl()).placeholder(R.drawable.scifi).error(R.drawable.scifi).into(holder.movieImg);

        Glide.with(context).asBitmap().load(actors.get(position).getPoster()).error(R.drawable.unavailable_photo).fallback(R.drawable.unavailable_photo).into(holder.actorImage);



    }

    @Override
    public int getItemCount() {
        return actors.size();
    }


    public void setActors(ArrayList<Actor> actors)
    {
        this.actors = actors;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView actorImage;
        private TextView actorName;
        private CardView actorItemParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.imgMovieItem);
            actorName = itemView.findViewById(R.id.txtViewMovieName);
            actorItemParent = itemView.findViewById(R.id.movietItemParent);
        }
    }
}
