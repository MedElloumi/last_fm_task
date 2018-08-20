package com.lastfm.dev.lastfm.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.adapters.ArtistsAdapter;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.ArtistSearchResponse;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.IndividualArtist;
import com.lastfm.dev.lastfm.presenters.remote.ArtistPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPageFragment extends Fragment implements ArtistPresenter.ArtistInterface {
    protected @BindView(R.id.toolbar) Toolbar toolbar;
    protected @BindView(R.id.searchEditText) EditText searchEditText;
    protected @BindView(R.id.artistListRecyclerView) RecyclerView artistListRecyclerView;
    protected @BindView(R.id.progressBar) ProgressBar progressBar;

    private ArrayList<IndividualArtist> artistList = new ArrayList<>();
    private ArtistsAdapter artistsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_page, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_screen_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // before making an api request, make sure artist_search edit text is not empty
                // => no need to allow users to make api requests without a mandatory parameter (@artist name)
                if (!TextUtils.isEmpty(searchEditText.getText().toString().trim())) {
                    progressBar.setVisibility(View.VISIBLE);
                    // return artists list that include the name provide by the user
                    // results will be fired in onArtistResponse/onArtistFailure
                    ArtistPresenter artistPresenter = new ArtistPresenter(this, searchEditText.getText().toString());
                    artistPresenter.returnArtistsList();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onArtistResponse(Response<ArtistSearchResponse> response) {
        // make sure that the fragment is attached to the activity when onArtistResponse is fired
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
            artistList.clear();
            artistList.addAll(response.body().getResults().getArtistMatches().getArtist());
            artistsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onArtistFailure(Throwable t) {
        if (isAdded()){
            progressBar.setVisibility(View.GONE);
        }
    }

    private void initializeRecyclerView() {
        artistsAdapter = new ArtistsAdapter(artistList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        artistListRecyclerView.setLayoutManager(mLayoutManager);
        artistListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        artistListRecyclerView.setAdapter(artistsAdapter);
    }
}
