package com.lastfm.dev.lastfm.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.activities.MasterActivity;
import com.lastfm.dev.lastfm.fragments.AlbumsOverviewFragment;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.IndividualArtist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elloumi on 16.08.18.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.MyViewHolder>{
    private ArrayList<IndividualArtist> artistList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected @BindView(R.id.artistName) TextView artistName;
        protected @BindView(R.id.artistImageView) ImageView imageView;
        protected @BindView(R.id.artistListeners) TextView artistListeners;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ArtistsAdapter(ArrayList<IndividualArtist> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.artist_list_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        IndividualArtist artist = artistList.get(i);
        // get third image from the image array {the one with size large}
        String artistImage = artist.getImage().get(2).getText();
        String artistName = artist.getName();
        String artistListeners = artist.getListeners();
        final String artistMbid = artist.getMbid();
        if (artistName != null) {
            holder.artistName.setText(artistName);
        }
        if (artistListeners != null) {
            holder.artistListeners.setText(artistListeners);
        }
        // verify that artist image path is not null or empty {empty ==> will fire picasso exception}
        if ((artistImage != null) && (!artistImage.isEmpty())) {
            Picasso.get().load(artistImage).placeholder(R.drawable.small_placeholder).into(holder.imageView);
        } else if (artistImage.isEmpty()) {
            Picasso.get().load(R.drawable.small_placeholder).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // I expect from the lastFm API to return Mbid {id} for every artist
                // but in case no Mbid is provided no need to open the albums overView fragment
                // ==> we will need this input {Mbid} to make a call to get top albums for a specific artist
                if ((artistMbid != null) && (!artistMbid.isEmpty())){
                    openAlbumsOverviewFragment(artistMbid);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    private void openAlbumsOverviewFragment(String Mbid) {
        MasterActivity masterActivity = (MasterActivity) context;
        Bundle albumsBundle = new Bundle();
        albumsBundle.putString(masterActivity.getString(R.string.mbid), Mbid);
        AlbumsOverviewFragment albumsOverviewFragment = new AlbumsOverviewFragment();
        albumsOverviewFragment.setArguments(albumsBundle);
        FragmentTransaction transaction = masterActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, albumsOverviewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
