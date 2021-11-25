package com.watchcorn.watchcorn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FilmTestAdapter extends ArrayAdapter<FilmTest> {
    private Context mContext;
    private int mRessource;

    ArrayList<FilmTest> objects;

    public FilmTestAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FilmTest> o) {
        super(context, resource,o);
        this.mContext = context;
        this.mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mRessource,parent,false);

        ImageView imageView = convertView.findViewById(R.id.haha);
        TextView TXTTitle = convertView.findViewById(R.id.text);
        TextView TXTDes = convertView.findViewById(R.id.text2);

        imageView.setImageResource(getItem(position).getmImage());
        TXTTitle.setText(getItem(position).getName());
        TXTDes.setText(getItem(position).getDes());

        return convertView;
    }

    public void update(ArrayList<FilmTest> results){
        objects = new ArrayList<>();
        objects.addAll(results);
        notifyDataSetChanged();
    }

}
