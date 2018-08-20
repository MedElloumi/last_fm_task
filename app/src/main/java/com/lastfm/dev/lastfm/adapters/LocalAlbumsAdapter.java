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
import com.lastfm.dev.lastfm.fragments.AlbumDetailFragment;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.Image;
import com.lastfm.dev.lastfm.models.LocalAlbum;
import com.lastfm.dev.lastfm.models.RequestedAlbum;
import com.lastfm.dev.lastfm.models.TopTracksResponse.IndividualTrack;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elloumi on 20.08.18.
 */
public class LocalAlbumsAdapter extends RecyclerView.Adapter<LocalAlbumsAdapter.MyViewHolder>{
    private ArrayList<LocalAlbum> localAlbums;
    private Context context;

    public LocalAlbumsAdapter(ArrayList<LocalAlbum> localAlbums, Context context) {
        this.localAlbums = localAlbums;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.local_album_row, viewGroup, false);
        return new LocalAlbumsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final LocalAlbum localAlbum = localAlbums.get(i);
        String localAlbumTitle = localAlbum.getName();
        String localAlbumImage = localAlbum.getAlbumImage();

        if (localAlbumTitle!=null) {
            myViewHolder.localAlbumTextView.setText(localAlbumTitle);
        }
        if ((localAlbumImage!=null)&&(!localAlbumImage.isEmpty())) {
            Picasso.get().load(localAlbumImage).placeholder(R.drawable.small_placeholder).into(myViewHolder.localAlbumImageView);
        } else if (localAlbumImage.isEmpty()){
            Picasso.get().load(R.drawable.small_placeholder).into(myViewHolder.localAlbumImageView);
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbumDetailFragment(localAlbum);
            }
        });

    }

    @Override
    public int getItemCount() {
        return localAlbums.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected @BindView(R.id.localAlbumImageView) ImageView localAlbumImageView;
        protected @BindView(R.id.localAlbumTextView) TextView localAlbumTextView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * open album detail fragment and pass the requested data from the task(album name - artist name - image) and tracks through bundle
     * @param album
     */
    private void openAlbumDetailFragment(LocalAlbum album){
        MasterActivity masterActivity = (MasterActivity) context;
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(masterActivity.getString(R.string.album),new RequestedAlbum(album.getName(),album.getArtist(),album.getAlbumImage()));
        bundle.putParcelable(masterActivity.getString(R.string.tracks),unfoldToIndividualTracks(album));
        albumDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = masterActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,albumDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * in short: we will need to change the data structure of the data from our local data, so we use the parcelable classes ==> please check AlbumsAdapter as it was implemented first
     * local storage {"tracks":"track1, track2, track3","images":"image link"} => as all tracks have the same image and yes tracks list name are concatenated
     * result expected ===> array list of tracks, where every track has his own image
     * @param album
     * @return
     */
    private TopTracks unfoldToIndividualTracks(LocalAlbum album){
        String albumTracks = album.getTracks(); // "a,b,c,d,e,f"
        String albumImages = album.getTracksImage();
        ArrayList<IndividualTrack> individualTracksLis = new ArrayList<>();
        ArrayList<Image> imageList = new ArrayList<>();
        Image image = new Image(albumImages);
        imageList.add(image);
        String[] albumTracksArray = albumTracks.split(",");
        for (int i = 0; i < albumTracksArray.length; i++) {
            individualTracksLis.add(new IndividualTrack(albumTracksArray[i],imageList));
        }
        return new TopTracks(individualTracksLis);
    }
}
