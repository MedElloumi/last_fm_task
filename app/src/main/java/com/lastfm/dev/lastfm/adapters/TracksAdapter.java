package com.lastfm.dev.lastfm.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.models.TopTracksResponse.IndividualTrack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.MyViewHolder>{
    private ArrayList<IndividualTrack> trackList;

    public TracksAdapter(ArrayList<IndividualTrack> trackList){
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_list_row, viewGroup, false);
        return new TracksAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        IndividualTrack track = trackList.get(i);
        String trackName = track.getName();
        String trackImage = track.getImage().get(0).getText();
        if (trackName != null) {
            myViewHolder.artistName.setText(trackName);
        }
        if ((trackImage!=null) && (!trackImage.isEmpty())) {
            Picasso.get().load(trackImage).placeholder(R.drawable.small_placeholder).into(myViewHolder.trackImageView);
        } else if (trackImage.isEmpty()){
            Picasso.get().load(R.drawable.small_placeholder).into(myViewHolder.trackImageView);
        }
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected @BindView(R.id.trackName) TextView artistName;
        protected @BindView(R.id.trackImageView) ImageView trackImageView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
