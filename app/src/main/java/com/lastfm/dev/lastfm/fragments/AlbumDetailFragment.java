package com.lastfm.dev.lastfm.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.adapters.TracksAdapter;
import com.lastfm.dev.lastfm.models.LocalAlbum;
import com.lastfm.dev.lastfm.models.RequestedAlbum;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracks;
import com.lastfm.dev.lastfm.presenters.local.LocalAlbumPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailFragment extends Fragment implements LocalAlbumPresenter.LocalAlbumInterface{
    protected @BindView(R.id.albumNameTextView) TextView albumName;
    protected @BindView(R.id.albumAuthorTextView) TextView albumArtist;
    protected @BindView(R.id.albumImageView) ImageView albumImage;
    protected @BindView(R.id.tracksRecyclerView) RecyclerView tracksRecyclerView;
    protected @BindView(R.id.saveLocalDb) RatingBar saveToLocalDbStar;

    private RequestedAlbum album;
    private TopTracks topTracks;
    private LocalAlbumPresenter.LocalAlbumInterface localAlbumInterface;

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_detail, container, false);
        ButterKnife.bind(this,view);
        localAlbumInterface = this;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            album = bundle.getParcelable(getString(R.string.album));
            topTracks = bundle.getParcelable(getString(R.string.tracks));
        }
        LocalAlbum localAlbum = new LocalAlbum();
        localAlbum.setName(album.getName());
        // check if the current album exist in our local storage or no, so we change the color of the star {rating bar}
        // result will be fired in onCheckAlbumExistence callback
        LocalAlbumPresenter localAlbumPresenter = new LocalAlbumPresenter(localAlbumInterface,getActivity(),localAlbum,getString(R.string.check_album_locally));
        localAlbumPresenter.execute();
        initViews();
        // old solution was based that tracks are album level (that means every album has its own tracks) before verifying that its incorrect.
        // tracks are artist level so no need to make an API call every time we open an Album, its better to make one call within the previous fragment

        // TrackPresenter trackPresenter= new TrackPresenter(this,album.getMbid());
        // trackPresenter.returnTopTracks();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * initialize views {album name, artist, image} and init tracks RecyclerView
     */
    private void initViews() {
        albumName.setText(album.getName());
        albumArtist.setText(album.getArtist());
        saveToLocalDbStar.setOnRatingBarChangeListener(new saveAlbumLocallyListener());
        if ((album.getImage() != null) && (!album.getImage().isEmpty())){
            Picasso.get().load(album.getImage()).placeholder(R.drawable.small_placeholder).into(albumImage);
        } else {
            Picasso.get().load(R.drawable.small_placeholder).into(albumImage);
        }
        TracksAdapter tracksAdapter = new TracksAdapter(topTracks.getIndividualTrack());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        tracksRecyclerView.setLayoutManager(mLayoutManager);
        tracksRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tracksRecyclerView.setAdapter(tracksAdapter);
    }

    private class saveAlbumLocallyListener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            // if (b): verify that action is coming from the user
            // because in case of opening the Album detail fragment, from the best albums screen (and we click on one album which is already saved in the local storage)
            // the "star" will change state from off (default state) to on, which will lead to fire onRatingChanged and insert again the album while its already inserted
            if (b) {
                if (ratingBar.getRating() == 1) {
                    saveAlbumLocally();
                } else {
                    deleteAlbumLocally();
                }
            }
        }
    }

    private void deleteAlbumLocally() {
        String method = getString(R.string.delete_album_locally);
        LocalAlbum localAlbum = new LocalAlbum();
        localAlbum.setName(album.getName());
        LocalAlbumPresenter localAlbumPresenter = new LocalAlbumPresenter(localAlbumInterface, getActivity(), localAlbum, method);
        localAlbumPresenter.execute();
    }

    private void saveAlbumLocally() {
        String method = getString(R.string.insert_album_locally);
        // all tracks have the same picture which is the artist picture as I noticed from the API
        // as a result => no need to save them all, we will save only one and we will use it every x time {x=number of tracks}.
        // individualTracks.get(0).getImage().get(0).getText() ==> get the first track => get first image (with size => small)
        String tracksImage = topTracks.getIndividualTrack().get(0).getImage().get(0).getText();
        // Android lint suggested to use StringBuilder for performance reasons
        StringBuilder tracksList = new StringBuilder();
        String separator = ",";
        // concat all tracks name
        // not sure if its the best approach
        for (int i = 0; i < topTracks.getIndividualTrack().size(); i++) {
            tracksList.append(topTracks.getIndividualTrack().get(i).getName()).append(separator);
        }
        LocalAlbum localAlbum = new LocalAlbum(album.getName(), album.getArtist(), album.getImage(),tracksImage, tracksList.toString());
        LocalAlbumPresenter localAlbumPresenter = new LocalAlbumPresenter(localAlbumInterface, getActivity(), localAlbum, method);
        localAlbumPresenter.execute();
    }

    /**
     * save Album locally callback: show toast
     */
    @Override
    public void onSaveAlbumLocally() {
        Toast.makeText(getActivity(), R.string.album_saved,Toast.LENGTH_LONG).show();
    }

    /**
     * Delete Album locally callback: show toast
     */
    @Override
    public void onDeleteAlbumLocally() {
        Toast.makeText(getActivity(), R.string.album_deleted,Toast.LENGTH_LONG).show();
    }

    /**
     * CheckAlbumExistence callback, it will return the number of rows (in the SQLite database) of a specific album
     * if the number of rows equal 1, that means that our album is present in our local storage, so we can fill our star
     * this approach is based on the uniqueness of the {album name}, otherwise it will not work as expected!
     * @param s
     */
    @Override
    public void onCheckAlbumExistence(Long s) {
        if (isAdded()) {
            if (s.intValue() == 1) {
                saveToLocalDbStar.setRating(1);
            }
        }
    }

    @Override
    public void onSelectAlbumsResponse(ArrayList<LocalAlbum> localAlbums) {

    }
}
