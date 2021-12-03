package com.watchcorn.watchcorn;

import android.content.Context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.titleMovie.setText(resMovie.get(position).getTitle());
        holder.durationMovie.setText(resMovie.get(position).getMovieLength() + "min");

        Glide.with(context).asBitmap().load(resMovie.get(position).getSmallImageUrl()).into(holder.coverMovie);

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
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private ImageView artistImg;
        private TextView titleMovie,durationMovie;
        private ImageView coverMovie;
        private RelativeLayout myLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.text);
            durationMovie = itemView.findViewById(R.id.text2);
            coverMovie = itemView.findViewById(R.id.haha);
            myLayout = itemView.findViewById(R.id.parent);


        }
    }
}
