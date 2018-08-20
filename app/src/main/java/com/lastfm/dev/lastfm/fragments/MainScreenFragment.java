package com.lastfm.dev.lastfm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.adapters.LocalAlbumsAdapter;
import com.lastfm.dev.lastfm.models.LocalAlbum;
import com.lastfm.dev.lastfm.presenters.local.LocalAlbumPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenFragment extends Fragment implements LocalAlbumPresenter.LocalAlbumInterface {
    protected @BindView(R.id.toolbar) Toolbar toolbar;
    protected @BindView(R.id.localAlbumsRecyclerView) RecyclerView localAlbumsRecyclerView;

    private LocalAlbumsAdapter localAlbumsAdapter;
    private ArrayList<LocalAlbum> localAlbums = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_screen_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SearchPageFragment searchPageFragment = new SearchPageFragment();
                fragmentTransaction.replace(R.id.container, searchPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // call LocalAlbumPresenter and request albums that are saved locally
        LocalAlbumPresenter localAlbumPresenter= new LocalAlbumPresenter(getActivity(),this,getString(R.string.select_albums_locally));
        localAlbumPresenter.execute();
        initializeRecyclerView();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * select albums locally callback
     * @param localAlbums
     */
    @Override
    public void onSelectAlbumsResponse(ArrayList<LocalAlbum> localAlbums) {
        this.localAlbums.clear();
        this.localAlbums.addAll(localAlbums);
        localAlbumsAdapter.notifyDataSetChanged();
    }

    private void initializeRecyclerView() {
        localAlbumsAdapter = new LocalAlbumsAdapter(localAlbums, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        localAlbumsRecyclerView.setLayoutManager(mLayoutManager);
        localAlbumsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        localAlbumsRecyclerView.setAdapter(localAlbumsAdapter);
    }

    /**
     * not needed and will not be fired here, check AlbumDetailFragment
     */
    @Override
    public void onSaveAlbumLocally() {

    }

    /**
     * not needed and will not be fired here, check AlbumDetailFragment
     */
    @Override
    public void onDeleteAlbumLocally() {

    }

    /**
     * not needed and will not be fired here, check AlbumDetailFragment
     * @param s
     */
    @Override
    public void onCheckAlbumExistence(Long s) {

    }


}
