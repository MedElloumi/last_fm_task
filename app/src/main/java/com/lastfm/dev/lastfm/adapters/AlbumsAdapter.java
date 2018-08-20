package com.lastfm.dev.lastfm.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.activities.MasterActivity;
import com.lastfm.dev.lastfm.fragments.AlbumDetailFragment;
import com.lastfm.dev.lastfm.models.TopAlbumsResponse.Album;
import com.lastfm.dev.lastfm.models.RequestedAlbum;
import com.lastfm.dev.lastfm.models.TopTracksResponse.IndividualTrack;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class AlbumsAdapter extends BaseAdapter{
    private LayoutInflater inflater;

    private ArrayList<Album> albumList;
    private TopTracks topTracks;
    private Context context;

    public AlbumsAdapter(Context context, ArrayList<Album> albumList, TopTracks topTracks) {
        this.context = context;
        this.albumList = albumList;
        this.topTracks = topTracks;
    }

    public static class ViewHolder {
        protected @BindView(R.id.albumImageView) ImageView albumImageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int i) {
        return albumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        // get third image from every album (the one with size large)
        final String albumImage = albumList.get(i).getImage().get(2).getText();
        final String albumName = albumList.get(i).getName();
        final String albumAuthor = albumList.get(i).getAlbumArtist().getName();
        final String mbid = albumList.get(i).getAlbumArtist().getMbid();
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.album_grid_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        // verify that artist image path is not null or empty {empty ==> will fire picasso exception}
        if ((albumImage!=null) && (!albumImage.isEmpty())) {
            Picasso.get().load(albumImage).placeholder(R.drawable.small_placeholder).into(holder.albumImageView);
        } else if (albumImage.isEmpty()){
            Picasso.get().load(R.drawable.small_placeholder).into(holder.albumImageView);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestedAlbum requestedAlbum = new RequestedAlbum(albumName,albumAuthor,albumImage,mbid);
                openAlbumDetailFragment(requestedAlbum);
            }
        });
        return view;
    }

    /**
     * open album detail fragment and pass the requested data from the task(album name - artist name - image) and tracks through bundle
     * @param album
     */
    private void openAlbumDetailFragment(RequestedAlbum album){
        MasterActivity masterActivity = (MasterActivity) context;
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(masterActivity.getString(R.string.album),album);
        bundle.putParcelable(masterActivity.getString(R.string.tracks),topTracks);
        albumDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = masterActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,albumDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
