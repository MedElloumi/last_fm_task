package com.lastfm.dev.lastfm.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.adapters.AlbumsAdapter;
import com.lastfm.dev.lastfm.models.TopAlbumsResponse.Album;
import com.lastfm.dev.lastfm.models.TopAlbumsResponse.TopAlbumsResponse;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracks;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracksResponse;
import com.lastfm.dev.lastfm.presenters.remote.AlbumPresenter;
import com.lastfm.dev.lastfm.presenters.remote.TrackPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsOverviewFragment extends Fragment implements AlbumPresenter.AlbumInterface, TrackPresenter.TracksInterface{
    protected @BindView(R.id.albumsGridView) GridView albumsGridView;
    protected @BindView(R.id.toolbar) Toolbar toolbar;
    protected @BindView(R.id.progressBar) ProgressBar progressBar;

    private ArrayList<Album> albumList = new ArrayList<>();
    private TopTracks topTracks = new TopTracks();
    private AlbumsAdapter albumsAdapter;
    private String mbid;

    public AlbumsOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums_overview, container, false);
        ButterKnife.bind(this,view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mbid = bundle.getString(getString(R.string.mbid));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (albumsAdapter == null) {
            albumsAdapter = new AlbumsAdapter(getActivity(), albumList,topTracks);
            albumsGridView.setAdapter(albumsAdapter);
            // call returnAlbumsList, results will be fired in onAlbumResponse/onAlbumFailure
            AlbumPresenter albumPresenter = new AlbumPresenter(this, mbid);
            albumPresenter.returnAlbumsList();
            progressBar.setVisibility(View.VISIBLE);
            // we should only initialize our adapter, call the presenter, when our adapter is null
            // or our gridView will be repopulated again when clicking back button from album detail fragment
        } else {
            albumsGridView.setAdapter(albumsAdapter);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAlbumResponse(Response<TopAlbumsResponse> response) {
        if (isAdded()) {
            albumList.addAll(response.body().getTopAlbums().getAlbum());

            // I think that we shouldnt notifyDataSetChanged here until we make the second call
            // albumsAdapter.notifyDataSetChanged();

            // when we receive the best albums response, we make a second call to get tracks related to this artist
            // result from this call will be fired in onTracksResponse/onTracksFailure
            TrackPresenter trackPresenter= new TrackPresenter(this,mbid);
            trackPresenter.returnTopTracks();
        }
    }

    @Override
    public void onAlbumFailure(Throwable t) {
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTracksResponse(Response<TopTracksResponse> response) {
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
            topTracks.setIndividualTrack(response.body().getTopTracks().getIndividualTrack());
            topTracks.setAttr(response.body().getTopTracks().getAttr());
            albumsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTracksFailure(Throwable t) {
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
