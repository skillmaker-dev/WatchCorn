package com.watchcorn.watchcorn;

import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewArtistsAdapter extends RecyclerView.Adapter<RecyclerViewArtistsAdapter.ViewHolder> {

    private ArrayList<Artist> artists = new ArrayList<>();
    private Context context;

    public RecyclerViewArtistsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_page_artist_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.artistName.setText(artists.get(position).getName());
        holder.artistItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, artists.get(holder.getAdapterPosition()).getName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

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
        return artists.size();
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView artistImg;
        private TextView artistName;
        private CardView artistItemParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artistImg = itemView.findViewById(R.id.imgArtistItem);
            artistName = itemView.findViewById(R.id.txtViewArtistName);
            artistItemParent = itemView.findViewById(R.id.artistItemParent);
        }
    }
}
